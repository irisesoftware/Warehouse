package in.irise.soft.batchconfig;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import in.irise.soft.model.Uom;
import in.irise.soft.service.IUomService;
import in.irise.soft.util.AppUtil;

@Configuration
@EnableBatchProcessing
public class UomBatchConfig {

	// a. reader object
	@Bean
	public ItemReader<Uom> readerUom() {
		FlatFileItemReader<Uom> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("E:/batchinput/uoms.csv"));
		/*try {
			reader.setResource(new UrlResource("http://abcd.com/uoms.csv"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		reader.setLineMapper(new DefaultLineMapper<Uom>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(",");
				setNames("uomType","uomModel","description");
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Uom>() {{
				setTargetType(Uom.class);
			}});
		}});
		return reader;
	}
	
	@Autowired
	private IUomService service;
	
	// b. processor object
	@Bean
	public ItemProcessor<Uom,Uom> processorUom() {
		return (ob) -> {
			ob.setUomModel(ob.getUomModel().toUpperCase());
			if(!AppUtil.getUomTypes().contains(ob.getUomType())) {
				ob.setUomType("NA");
			}
			ob.setDescription(ob.getDescription().toUpperCase());
			if(service.isUomModelExist(ob.getUomModel())) {
				return null;
			} else {
				return ob;
			}
		};
	}
	
	@Autowired
	private EntityManagerFactory entityManagerFactory ;
	
	// c. writer object
	@Bean
	public ItemWriter<Uom> writerUom() {
		JpaItemWriter<Uom> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}
	
	// d. listener object
	@Bean
	public JobExecutionListener listenerUom() {
		return new JobExecutionListener() {
			
			public void beforeJob(JobExecution je) {
				System.out.println("Started with "+je.getStatus());
			}
			
			public void afterJob(JobExecution je) {
				System.out.println("Finished with "+je.getStatus());
			}
		};
	}
	
	// f. step builder factory
	@Autowired
	public StepBuilderFactory sf;
	
	// g. step object
	@Bean
	public Step stepUom() {
		return sf.get("stepUom") //step name
				.<Uom,Uom>chunk(100) //one batch size
				.reader(readerUom()) //reader object name
				.processor(processorUom()) //processor object name
				.writer(writerUom())//writer object name
				.build(); 
	}
	
	// h. job builder factory
	@Autowired
	private JobBuilderFactory jf;
	
	// i. job object
	@Bean
	public Job jobUom() {
		return jf.get("jobUom")
				.listener(listenerUom())
				.incrementer(new RunIdIncrementer())
				.start(stepUom())
				.build()
				;
	} 
}
