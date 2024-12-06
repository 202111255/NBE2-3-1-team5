package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.cart.*;
import com.example.coffee.model.user.RequestMemberDTO;
import com.example.coffee.repository.CartMapper;
import com.example.coffee.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    private MemberMapper memberMapper;

    // 장바구니 상품 목록 조회
    public Result viewCartList(Long cartId) {
        try {

            // 오류 확인 ==================================================================================

            // 1. 장바구니 ID 확인
            // 해당 cartId가 존재하지 않는 경우
            if (cartMapper.selectCartId(cartId) == null) {
                return new Result(ResultCode.USER_NOT_FOUND);
            }

            // 2. 장바구니 상품 목록 확인
            // cartId에 해당하는 상품 목록이 없는 경우
            if (cartMapper.viewCartList(cartId).isEmpty()) {
                return new Result(ResultCode.CART_NOT_FOUND);
            }
            // 정상 처리 ==================================================================================

            // 1. 상품 리스트 ResponseDTO 저장
            CartViewResponseDTO cartViewResponseDTO = new CartViewResponseDTO();
            cartViewResponseDTO.setCartViewResponseDTOS(cartMapper.viewCartList(cartId));

            // 2. 장바구니의 세부 정보 (총 개수, 총 가격,) ResponseDTO 저장
            ViewDetailsDTO viewDetail = cartMapper.getViewDetails(cartId);
            cartViewResponseDTO.setTotalQuantity(viewDetail.getTotalQuantity());
            cartViewResponseDTO.setTotalPrice(viewDetail.getTotalPrice());

            // 결과 반환
            return new Result(ResultCode.SUCCESS, cartViewResponseDTO);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 시스템 예외 처리
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    // 장바구니 상품 추가
    public Result cartProductAdd(CartAddRequestDTO request) {
        try {
            // 오류 확인 ==================================================================================

            // 1. 요청 검증
            // 잘못된 요청: 수량이 0 이하인 경우
            if (request.getQuantity() <= 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }

            // 2. 장바구니 ID 확인
            // 해당 cartId가 존재하지 않는 경우
            if (cartMapper.selectCartId(request.getCartId()) == null) {
                return new Result(ResultCode.USER_NOT_FOUND);
            }

            // 3. 상품 ID 확인
            // 해당 productId가 존재하지 않는 경우
            if (cartMapper.getProductById(request.getProductId()) == null) {
                return new Result(ResultCode.PRODUCT_NOT_FOUND);
            }

            // 4. 중복 상품 확인
            // 장바구니에 이미 추가된 상품인지 확인
            int count = cartMapper.countProductByCartIdAndProductId(request);
            if (count > 0) {
                return new Result(ResultCode.ITEM_ALREADY_IN_CART);
            }

            // 정상 처리 ==================================================================================

            // 1. 추가할 상품 정보를 매핑
            Map<String, Object> params = new HashMap<>();
            params.put("cartId", request.getCartId());       // 장바구니 ID
            params.put("productId", request.getProductId()); // 상품 ID
            params.put("quantity", request.getQuantity());   // 요청 수량

            // 2. 상품 가격 계산 후 매핑
            int price = cartMapper.getPriceById(request.getProductId()) * request.getQuantity();
            params.put("price", price);

            // 3. 데이터베이스 작업
            cartMapper.cartProductAdd(params);              // 상품 추가
            cartMapper.updateTotals(request.getCartId());   // 장바구니 총 개수 및 총 가격 업데이트

            // 결과 반환
            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 시스템 예외 처리
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    // 장바구니 상품 개수 변경
    public Result updateCartList(CartUpdateRequestDTO request) {
        try {
            // 오류 확인 ==================================================================================

            // 1. 장바구니 ID 확인
            // 해당 cartId가 존재하지 않는 경우
            if (cartMapper.selectCartId(request.getCartId()) == null) {
                return new Result(ResultCode.USER_NOT_FOUND);
            }

            // 2. 장바구니 내 상품 확인
            // 해당 cartId에 productId가 포함되어 있지 않은 경우
            FindProductInCartDTO findDTO = new FindProductInCartDTO();
            findDTO.setCartId(request.getCartId());
            findDTO.setProductId(request.getProductId());
            if (cartMapper.findProductInCart(findDTO) == null) {
                return new Result(ResultCode.PRODUCT_NOT_FOUND);
            }

            // 3. 상품 수량 < 0 인 경우
            if (request.getQuantity() < 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }
            // 정상 처리 ==================================================================================

            // 1. 상품 수량을 0으로 업데이트하려는 경우, 해당 상품을 삭제 처리
            if (request.getQuantity() == 0) {
                CartDeleteRequestDTO deleteDTO = new CartDeleteRequestDTO();
                deleteDTO.setCartId(request.getCartId());
                deleteDTO.setProductId(request.getProductId());

                cartMapper.deleteCartList(deleteDTO); // 상품 삭제
                cartMapper.updateTotals(request.getCartId()); // 장바구니 총 개수 및 총 가격 업데이트
                return new Result(ResultCode.SUCCESS);
            }

            // 2. 상품 개수 및 가격 정보 업데이트
            UpdateProductDetailsDTO updateDTO = new UpdateProductDetailsDTO();
            updateDTO.setCartId(request.getCartId());
            updateDTO.setProductId(request.getProductId());
            updateDTO.setQuantity(request.getQuantity());
            updateDTO.setPrice(request.getQuantity() * cartMapper.getPriceById(request.getProductId())); // 가격 계산

            // 3. 데이터베이스 작업
            cartMapper.updateCartList(updateDTO); // 상품 정보 업데이트
            cartMapper.updateTotals(request.getCartId()); // 장바구니 총 개수 및 총 가격 업데이트

            // 결과 반환
            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 일반적인 시스템 예외 처리
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    // 장바구니 상품 삭제
    public Result deleteCartList(CartDeleteRequestDTO request) {
        try {
            // 토큰 인증으로 cartId 가져오기
            //request.setCartId(cartMapper.getCartIdByMemberId(userId));

            // 오류 확인 ==================================================================================

            // 1. 장바구니 ID 확인
            // 해당 cartId가 존재하지 않는 경우
            if (cartMapper.selectCartId(request.getCartId()) == null) {
                return new Result(ResultCode.USER_NOT_FOUND);
            }

            // 2. 장바구니 내 상품 확인
            // 해당 cartId에 productId가 포함되어 있지 않은 경우
            FindProductInCartDTO findDTO = new FindProductInCartDTO();
            findDTO.setCartId(request.getCartId());
            findDTO.setProductId(request.getProductId());
            if (cartMapper.findProductInCart(findDTO) == null) {
                return new Result(ResultCode.PRODUCT_NOT_FOUND);
            }

            // 정상 처리 ==================================================================================

            // 1. 상품 삭제
            cartMapper.deleteCartList(request);

            // 2. 장바구니 총 개수 및 총 가격 업데이트
            cartMapper.updateTotals(request.getCartId());

            // 결과 반환
            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 일반적인 시스템 예외 처리
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }
}
