package com.example.coffee.service;

import com.example.coffee.common.Result;
import com.example.coffee.common.ResultCode;
import com.example.coffee.model.cart.*;
import com.example.coffee.model.user.RequestMemberDTO;
import com.example.coffee.repository.CartMapper;
import com.example.coffee.repository.MemberMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class CartService {
    private final CartMapper cartMapper;
    private final MemberMapper memberMapper;

    // 장바구니 상품 목록 조회
    public Result viewCartList(Long userId) {
        try {
            // cartId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(userId);
            Long Id = member.getMemberId();
            Long cartId = cartMapper.getCartIdByMemberId(Id);
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
            System.out.println(dae.getMessage());
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 시스템 예외 처리
            System.out.println(e.getMessage());
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    public Result createCart(Long userId) {
        try {
            // 정상 처리 ==================================================================================

            // cartId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(userId);
            Long Id = member.getMemberId();
            Long cartId = cartMapper.getCartIdByMemberId(Id);

            // 1. 데이터베이스 작성
            cartMapper.createCart(userId);

            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            System.out.println(dae.getMessage());
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 시스템 예외 처리
            System.out.println(e.getMessage());
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    // 장바구니 상품 추가
    public Result cartProductAdd(Long userId, CartAddRequestDTO request) {
        try {
            // cartId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(userId);
            Long Id = member.getMemberId();
            Long cartId = cartMapper.getCartIdByMemberId(Id);

            // 오류 확인 ==================================================================================

            // 1. 요청 검증
            // 잘못된 요청: 수량이 0 이하인 경우
            if (request.getQuantity() <= 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }

            // 2. 상품 ID 확인
            // 해당 productId가 존재하지 않는 경우
            if (cartMapper.getProductById(request.getProductId()) == null) {
                return new Result(ResultCode.PRODUCT_NOT_FOUND);
            }

            // 3. 중복 상품 확인
            // 장바구니에 이미 추가된 상품인지 확인
            Long productId = request.getProductId();
            int count = cartMapper.countProductByCartIdAndProductId(cartId, productId);
            if (count > 0) {
                return new Result(ResultCode.ITEM_ALREADY_IN_CART);
            }

            // 정상 처리 ==================================================================================

            // 1. 추가할 상품 정보를 매핑
            Map<String, Object> params = new HashMap<>();
            params.put("cartId", cartId);       // 장바구니 ID
            params.put("productId", request.getProductId()); // 상품 ID
            params.put("quantity", request.getQuantity());   // 요청 수량

            // 2. 상품 가격 계산 후 매핑
            int price = cartMapper.getPriceById(request.getProductId()) * request.getQuantity();
            params.put("price", price);

            // 3. 데이터베이스 작업
            cartMapper.cartProductAdd(params);              // 상품 추가
            cartMapper.updateTotals(cartId);   // 장바구니 총 개수 및 총 가격 업데이트

            // 결과 반환
            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            System.out.println(dae.getMessage());
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 시스템 예외 처리
            System.out.println(e.getMessage());
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    // 장바구니 상품 개수 변경
    public Result updateCartList(Long userId, CartUpdateRequestDTO request) {
        try {
            // cartId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(userId);
            Long Id = member.getMemberId();
            Long cartId = cartMapper.getCartIdByMemberId(Id);
            // 오류 확인 ==================================================================================

            // 1. 상품 수량 < 0 인 경우
            if (request.getQuantity() < 0) {
                return new Result(ResultCode.INVALID_PARAMETER);
            }

            // 해당 cartId에 productId가 포함되어 있지 않은 경우
            FindProductInCartDTO findDTO = new FindProductInCartDTO();
            findDTO.setCartId(cartId);
            findDTO.setProductId(request.getProductId());
            if (cartMapper.findProductInCart(findDTO) == null) {
                return new Result(ResultCode.PRODUCT_NOT_FOUND);
            }

            // 정상 처리 ==================================================================================

            // 1. 상품 수량을 0으로 업데이트하려는 경우, 해당 상품을 삭제 처리
            if (request.getQuantity() == 0) {
                Map<String, Object> params = new HashMap<>();
                params.put("cartId", cartId);       // 장바구니 ID
                params.put("productId", request.getProductId()); // 상품 ID

                cartMapper.deleteCartList(params); // 상품 삭제
                cartMapper.updateTotals(cartId); // 장바구니 총 개수 및 총 가격 업데이트
                return new Result(ResultCode.SUCCESS);
            }

            // 2. 상품 개수 및 가격 정보 업데이트
            UpdateProductDetailsDTO updateDTO = new UpdateProductDetailsDTO();
            updateDTO.setCartId(cartId);
            updateDTO.setProductId(request.getProductId());
            updateDTO.setQuantity(request.getQuantity());
            updateDTO.setPrice(request.getQuantity() * cartMapper.getPriceById(request.getProductId())); // 가격 계산

            // 3. 데이터베이스 작업
            cartMapper.updateCartList(updateDTO); // 상품 정보 업데이트
            cartMapper.updateTotals(cartId); // 장바구니 총 개수 및 총 가격 업데이트

            // 결과 반환
            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            System.out.println(dae.getMessage());
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // 일반적인 시스템 예외 처리
            System.out.println(e.getMessage());
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }

    // 장바구니 상품 삭제
    public Result deleteCartList(Long userId, Long productId) {
        try {
            // cartId 가져오기
            RequestMemberDTO member = memberMapper.userInfo(userId);
            Long Id = member.getMemberId();
            Long cartId = cartMapper.getCartIdByMemberId(Id);
            // 오류 확인 ==================================================================================

            // 1. 장바구니 내 상품 확인
            // 해당 cartId에 productId가 포함되어 있지 않은 경우
            FindProductInCartDTO findDTO = new FindProductInCartDTO();
            findDTO.setCartId(cartId);
            findDTO.setProductId(productId);
            if (cartMapper.findProductInCart(findDTO) == null) {
                return new Result(ResultCode.PRODUCT_NOT_FOUND);
            }

            // 정상 처리 ==================================================================================

            // 1. 상품 삭제
            Map<String, Object> params = new HashMap<>();
            params.put("cartId", cartId);       // 장바구니 ID
            params.put("productId", productId); // 상품 ID
            cartMapper.deleteCartList(params); // 상품 삭제

            // 2. 장바구니 총 개수 및 총 가격 업데이트
            cartMapper.updateTotals(cartId);

            // 결과 반환
            return new Result(ResultCode.SUCCESS);

        } catch (DataAccessException dae) {
            // 데이터베이스 관련 예외 처리
            System.out.println(dae.getMessage());
            return new Result(ResultCode.DB_ERROR);
        } catch (Exception e) {
            // 일반적인 시스템 예외 처리
            System.out.println(e.getMessage());
            return new Result(ResultCode.SYSTEM_ERROR);
        }
    }
}

