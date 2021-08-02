package Projet7.batchMail.batch.step;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemWriterLogin implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {

    }
}
