package in.irise.soft.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UomBatchRunner {
	
	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	private Job jobUom;
	
	@Scheduled(cron = "0 36 11 * * *")
	public void readCsvUomBatch() throws Exception {
		launcher.run(jobUom, 
				new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters());
	}
}
