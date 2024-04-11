//package inhatc.cse.springboot.greeda62project.validator;
//
//import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
//import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.executable.ExecutableValidator;
//import jakarta.validation.metadata.BeanDescriptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//
//import java.util.Set;
//
//@RequiredArgsConstructor
//@Component
//public class CheckIdValidator extends AbstractValidator<>{
//
//    private MemberRepository memberRepository;
//
//    protected void doValidate(MemberDTO dto, Errors errors) {
//        if(memberRepository.existsById(dto.getId())) {
//            errors.rejectValue("id", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
//        }
//    }
//
//    @Override
//    protected void doValidate(Object dto, Errors errors) {
//
//    }
//
//
//}
