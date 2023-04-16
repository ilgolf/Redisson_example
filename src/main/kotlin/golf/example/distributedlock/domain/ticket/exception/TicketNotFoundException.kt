package golf.example.distributedlock.domain.ticket.exception

import golf.example.distributedlock.global.exception.error.BusinessException
import golf.example.distributedlock.global.exception.error.ErrorCode

class TicketNotFoundException : BusinessException(ErrorCode.TICKET_NOT_FOUND) {

}
