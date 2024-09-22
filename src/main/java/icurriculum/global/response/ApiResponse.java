package icurriculum.global.response;

import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.response.status.SuccessStatus;

public record ApiResponse<T>(
    Boolean isSuccess,
    String code,
    String message,
    T result) {

    public static final ApiResponse<Void> OK = new ApiResponse<>(true, SuccessStatus.OK.getCode(),
        SuccessStatus.OK.getMessage(), null);

    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(),
            result);
    }

    public static <T> ApiResponse<T> onFailure(ErrorStatus errorStatus, T data) {
        return new ApiResponse<>(false, errorStatus.getCode(), errorStatus.getMessage(), data);
    }

}
