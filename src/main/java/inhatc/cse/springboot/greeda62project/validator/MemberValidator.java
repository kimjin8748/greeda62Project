package inhatc.cse.springboot.greeda62project.validator;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required", "아이디를 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "비밀번호를 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required", "이름을 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "이메일을 입력해주세요.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "address.required", "주소를 입력해주세요.");

        MemberDTO memberDTO = (MemberDTO) target;

        MemberService memberService = null;
        if (memberService.checkDuplicateId(memberDTO.getId())) {
            errors.rejectValue("id", "id.duplicated", "중복된 아이디입니다.");
        }

        if (memberDTO.getPassword().length() < 8 || memberDTO.getPassword().length() > 20) {
            errors.rejectValue("password", "password.length", "비밀번호는 8자 이상 20자 이하로 입력해주세요.");
        }
    }
}
