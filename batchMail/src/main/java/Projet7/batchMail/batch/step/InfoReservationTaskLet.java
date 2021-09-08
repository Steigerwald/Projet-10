package Projet7.batchMail.batch.step;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class InfoReservationTaskLet implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(InfoReservationTaskLet.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("InfoReservation step initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("Info reservation au user concerné");
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("InfoReservation step ended.");
        return ExitStatus.COMPLETED;
    }
}
