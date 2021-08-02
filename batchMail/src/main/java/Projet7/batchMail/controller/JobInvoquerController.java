package Projet7.batchMail.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobInvoquerController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job jobProcessor;

    @RequestMapping("/JobProcess")
    public String handle() throws Exception{
        JobParameters jobParameters=new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(jobProcessor,jobParameters);
        return "Le job a été exécuté convenablement et jusqu'au bout";
    }
}
