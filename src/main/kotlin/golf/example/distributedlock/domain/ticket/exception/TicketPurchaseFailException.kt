package golf.example.distributedlock.domain.ticket.exception

import golf.example.distributedlock.global.exception.error.BusinessException
import golf.example.distributedlock.global.exception.error.ErrorCode

class TicketPurchaseFailException : BusinessException(ErrorCode.TICKET_PURCHASE_FAIL) {

}
