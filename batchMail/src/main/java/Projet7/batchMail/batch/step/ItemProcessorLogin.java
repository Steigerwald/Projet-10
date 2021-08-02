package Projet7.batchMail.batch.step;

import Projet7.batchMail.service.AuthService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessorLogin implements ItemProcessor<String,String> {

    @Autowired
    public AuthService authService;

    @Override
    public String process(String s) throws Exception {
        authService.setMemoireToken(s);
        System.out.println(s);
        return s;
    }
}
