package org.example.grip_demo.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterUserDTO {
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "username은 영문 소문자와 숫자 4~20자리여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{4,20}",
            message = "비밀번호는 문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 4~20자리여야 합니다.")
    private String password;


    private String name;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
    private String nickName;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;

    private String address;

    @Pattern(regexp = "^[0-9]{10,13}$" , message = "휴대폰 번호는 -를 제외한 숫자 9~11자리여야 합니다.")
    private String phoneNumber;

    private Boolean gender = true;
}
