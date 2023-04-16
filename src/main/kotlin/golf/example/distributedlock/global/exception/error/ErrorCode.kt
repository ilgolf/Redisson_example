package golf.example.distributedlock.global.exception.error

enum class ErrorCode(val message: String, val status: Int) {

    // common
    INVALID_INPUT_VALUE("올바르지 않은 값입니다.", 400),
    METHOD_NOT_ALLOWED("올바르지 않은 요청 메서드입니다.", 405),
    INTERNAL_SERVER_ERROR("치명적인 서버 오류입니다.", 500),
    HANDLE_ACCESS_DENIED("해당권한으로는 접근할 수 없습니다", 403),
    INVALID_TYPE_VALUE("해당 값은 들어올 수 없습니다. 값을 확인해주세요", 400),

    // member
    MEMBER_NOT_FOUND("회원을 찾지 못하였습니다.", 400),
    DUPLICATE_NICKNAME("닉네임이 중복됩니다.", 400),
    DUPLICATE_EMAIL("이메일이 중복됩니다.", 400),

    // auth
    INVALID_REFRESH_TOKEN("올바르지 않은 토큰이 들어왔습니다. 다시 로그인 바랍니다.", 401),

    // ticket
    TICKET_NOT_FOUND("티켓을 찾을 수 없습니다. 문의 바랍니다.", 400),
    TICKET_PURCHASE_FAIL("티켓이 모두 소진되어 구매에 실패했습니다.", 400),
}
