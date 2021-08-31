package Projet7.batchMail.batch;

import Projet7.batchMail.batch.step.HelloWordTaskLet;
import Projet7.batchMail.batch.step.ItemReaderLogin;
import Projet7.batchMail.dto.ReservationDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<String> itemReader;

    @Qualifier("itemReaderReservation")
    @Autowired
    private ItemReader<ReservationDTO> reservationDTOItemReader;

    @Qualifier("itemReaderUser")
    @Autowired
    private ItemReader<ReservationDTO> userDTOItemReader;

    @Autowired
    private ItemProcessor<String,String> itemProcessor;

    @Autowired
    private ItemProcessor<ReservationDTO,ReservationDTO> itemProcessorReservation;

    @Autowired
    private ItemProcessor<ReservationDTO,ReservationDTO> itemProcessorUser;

    @Autowired
    private ItemWriter<String> itemWriter;

    @Autowired
    private ItemWriter<ReservationDTO> itemWriterReservation;

    @Autowired
    private ItemWriter<ReservationDTO> itemWriterUser;


    @Bean
    public ItemReader<String> itemReader() throws IOException, InterruptedException {
        return new ItemReaderLogin();
    }

    @Bean
    public Step helloWordStep(){
        return stepBuilderFactory.get("Step1")
                .tasklet(new HelloWordTaskLet())
                .build();
    }

    @Bean
    public Step connectingStep(){
        return stepBuilderFactory.get("Step2")
                .<String,String>chunk(1)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step reservationStep(){
        return stepBuilderFactory.get("Step3")
                .<ReservationDTO,ReservationDTO>chunk(1)
                .reader(reservationDTOItemReader)
                .processor(itemProcessorReservation)
                .writer(itemWriterReservation)
                .build();
    }

    @Bean
    public Step userStep(){
        return stepBuilderFactory.get("Step4")
                .<ReservationDTO,ReservationDTO>chunk(3)
                .reader(userDTOItemReader)
                .processor(itemProcessorUser)
                .writer(itemWriterUser)
                .build();
    }

    @Bean
    public Job helloWordJob(){
        return jobBuilderFactory.get("Job")
                .start(helloWordStep())
                .next(connectingStep())
                .next(reservationStep())
                .next(userStep())
                .build();
    }
/*
    @Bean
    public Job livreDispoJob(){
        return jobBuilderFactory.get("LivreDispoJob")
                .start(helloWordStep())
                .next(connectingStep())
                .next(reservationStep())
                .next(userStep())
                .build();
    }

 */


}
