package golf.example.distributedlock.domain.member.presentation

import golf.example.distributedlock.domain.member.application.MemberCommandService
import golf.example.distributedlock.domain.member.dto.request.MemberApiSaveRequestDto
import golf.example.distributedlock.domain.member.dto.request.MemberApiUpdateRequestDto
import golf.example.distributedlock.domain.member.dto.response.SimpleMemberResponse
import golf.example.distributedlock.domain.member.error.MemberException
import golf.example.distributedlock.global.security.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v2")
class MemberController(
    private val memberCommandService: MemberCommandService
) {

    @PostMapping("/public/members")
    fun save(@Valid @RequestBody requestDto: MemberApiSaveRequestDto): ResponseEntity<SimpleMemberResponse> {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                SimpleMemberResponse(
                    memberCommandService.save(
                        requestDto.toServiceDto()
                    ), true
                )
            )
    }

    @PutMapping("/members")
    fun update(@Valid @RequestBody memberApiUpdateRequestDto: MemberApiUpdateRequestDto,
               @AuthenticationPrincipal customUserDetails: CustomUserDetails
    ): ResponseEntity<Unit> {

        memberCommandService.update(memberApiUpdateRequestDto.toService(), customUserDetails.memberId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/members")
    fun delete(@AuthenticationPrincipal customUserDetails: CustomUserDetails): ResponseEntity<Unit> {

        memberCommandService.delete(customUserDetails.memberId)
        return ResponseEntity.noContent().build()
    }
}

