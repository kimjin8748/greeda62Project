package inhatc.cse.springboot.greeda62project.controller.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @PostMapping("/callback")
    public void receiveJsonData(@RequestBody JsonData jsonData) {
        List<String> result = jsonData.getResult();
        // 수신한 JSON 데이터 처리 로직
        for (String item : result) {
            System.out.println(item);
        }
    }

    static class JsonData {
        private List<String> result;

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }

}
