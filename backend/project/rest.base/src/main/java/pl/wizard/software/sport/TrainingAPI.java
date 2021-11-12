package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wizard.software.login.LoginService;

@RestController
@RequestMapping("/v2/trainings")
@Slf4j
@RequiredArgsConstructor
public class TrainingAPI {

    private final TrainingService trainingService;
    private final LoginService loginService;

    
}
