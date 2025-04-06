package com.caritrainc.backend

import com.caritrainc.backend.testcontainers.TestContainerInitializer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

//@Import(TestcontainersConfiguration::class)
@SpringBootTest
@ActiveProfiles("unitTest")
class CaritraApplicationTests {

    val testContainerInitializer: TestContainerInitializer = TestContainerInitializer()

    val UUID_REG_EXP = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\$"
    val TIMESTAMP_REG_EXP = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}[+-]\\d{2}:\\d{2}$"
    val TEST_USER_ID = "067053f8-eb65-11ef-af17-f3da1e3f18ca"
    val TEST_INVALID_USER_ID = "00000000-0000-0000-0000-000000000000"
    val VALID_JWT_TOKEN =
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwMTk1NDZhOC03YWY4LTdlZjMtOTVjYy02NGZhNjBkZGI0NTgiLCJlbWFpbCI6ImFuYW5kLmxpbmdhcmFqQGdtYWlsLmNvbSIsIm5hbWUiOiJhbmFuZC5saW5nYXJhakBnbWFpbC5jb20iLCJmaXJzdE5hbWUiOiJBZG1pbiIsImxhc3ROYW1lIjoiVXNlciIsIm9pX3NjcCI6WyJvcGVuaWQiLCJlbWFpbCIsInByb2ZpbGUiLCJyb2xlcyIsImFwaSJdLCJvaV9yc3JjIjoicmVzb3VyY2Vfc2VydmVyIiwibmJmIjoxNzQwNjQ4ODc0LCJleHAiOjMzMjk3NTU3Njc0LCJpYXQiOjE3NDA2NDg4NzQsImlzcyI6Imh0dHBzOi8vbG9jYWxob3N0OjcwOTUvb2F1dGgyL3YyLjAiLCJhdWQiOiJodHRwczovL2xvY2FsaG9zdDo0MjAwLyJ9.MdTx2iDM_ekuvVY-wGSLbThaUEkl-Rn1tKufaQwYWXfYAEn8zSSA4Bgcn4aeRKy6HUhY1tYRIohSP7sEuMwMfNMzz-cfPfhZro9XfXuXCgvzSF9BSInCBa5hHRvFx6zhbI3u69c02BHOeR9T6C0jDxWcZZ4OJoNb52EN3MRpYG3hH127bF-7IlrdtwfPB2cSM6gF_ewGE9gnDzGnAPijXsf3tjt0t31GowfK0E0jRLrJFhiKqYwpS385m6OxXLgb8H4gpIE-r01PkBQD1f5D-qI_LimhmyCeuevYRPtf9ouwLNCh2H0EfTRZDjRp3QzcfgfjsAxu_XeaXw7LRIjHCA"
    val INVALID_JWT_TOKEN =
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwMTk1NDZhOC03YWY4LTdlZjMtOTVjYy02NGZhNjBkZGI0NTgiLCJlbWFpbCI6ImFuYW5kLmxpbmdhcmFqQGdtYWlsLmNvbSIsIm5hbWUiOiJhbmFuZC5saW5nYXJhakBnbWFpbC5jb20iLCJmaXJzdE5hbWUiOiJBZG1pbiIsImxhc3ROYW1lIjoiVXNlciIsIm9pX3NjcCI6WyJvcGVuaWQiLCJlbWFpbCIsInByb2ZpbGUiLCJyb2xlcyIsImFwaSJdLCJvaV9yc3JjIjoicmVzb3VyY2Vfc2VydmVyIiwibmJmIjoxNzQwNjQ4OTk4LCJleHAiOjE3NDA2NDkwNTgsImlhdCI6MTc0MDY0ODk5OCwiaXNzIjoiaHR0cHM6Ly9sb2NhbGhvc3Q6NzA5NS9vYXV0aDIvdjIuMCIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0OjQyMDAvIn0.VK_LkdC8f-zkite1-06eaJgLkCr6-Ws86NTP8he3b3k_QyRiTM4HhpX8pmTKQ3hLWjcdSzJnQ6igm5WSO_DUHcljOPaXByH8FoADYjiFTtOFkdKXTgs9a9HLrdGOqRMhFq-Sd_VjWxKWLxRYnHp_xcfX9zAzNbpxip3iBKeUsL-vJh46PmndNBqOpaMCWSPXSeK30cG_PpdGZbaD4IqLtD9Olk4f0QXWVySvwE_S6F5aihF2TjdoV3HHIG_U1gsAW8ltnFoHU_G6K-Vfw5-jPt4odByXtxkow9IjRRRwknF51uj2r_ukZjnOGyD6ned9sSLes49Yc1-nDGZc-79RZw"

    @BeforeEach
    fun init() {
        testContainerInitializer.reset()
    }

    @AfterEach
    fun reset() {
        testContainerInitializer.seed()
    }

    @Test
    fun contextLoads() {
    }

}
