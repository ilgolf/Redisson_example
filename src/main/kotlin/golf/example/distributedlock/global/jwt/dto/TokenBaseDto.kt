package golf.example.distributedlock.global.jwt.dto

data class TokenBaseDto(
    var accessToken: String,
    var refreshToken: String
)