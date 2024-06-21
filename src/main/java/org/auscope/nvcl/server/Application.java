package org.auscope.nvcl.server;

import java.sql.SQLException;

import javax.jms.ConnectionFactory;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.dbcp2.BasicDataSource;
import org.auscope.nvcl.server.dao.DomainDataDao;
import org.auscope.nvcl.server.dao.DownSampledClassDataDao;
import org.auscope.nvcl.server.dao.DownSampledFloatDataDao;
import org.auscope.nvcl.server.dao.LogExtentsDao;
import org.auscope.nvcl.server.dao.NVCLDataSvcDao;
import org.auscope.nvcl.server.service.NVCLBlobStoreAccessSvc;
import org.auscope.nvcl.server.service.NVCLDataSvc;
import org.auscope.nvcl.server.service.NVCLDownloadGateway;
import org.auscope.nvcl.server.service.NVCLDownloadMessageConverter;
import org.auscope.nvcl.server.service.NVCLDownloadQueueBrowser;
import org.auscope.nvcl.server.service.NVCLDownloadSvc;
import org.auscope.nvcl.server.service.TSGDownloadRequestSvc;
import org.auscope.nvcl.server.vo.BoreholeViewVo;
import org.auscope.nvcl.server.vo.ConfigVo;
import org.auscope.nvcl.server.vo.DatasetCollectionVo;
import org.auscope.nvcl.server.vo.gmlPointVo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

@SpringBootApplication
@Configuration
public class Application extends SpringBootServletInitializer  {

    private BasicDataSource ds = null;
    private ConfigVo config = null;
    private JmsTemplate jmsTemplate = null;
    private NVCLDownloadMessageConverter nvclDownloadMessageConverter = null;
    private ActiveMQQueue tsgRequestDestination = null;
    private ActiveMQQueue tsgReplyDestination = null;
    private NVCLDownloadGateway nvclDownloadGateway = null;
    private NVCLDownloadSvc nvclDownloadSvc = null;
    private TSGDownloadRequestSvc tsgDownloadRequestSvc = null;
    private NVCLDownloadQueueBrowser nvclDownloadQueueBrowser = null;
    private MessageListenerAdapter tsgDownloadRequestListener = null;
    private SimpleMessageListenerContainer tsgDownloadRequestContainer = null;
    private JavaMailSenderImpl mailSender = null;
    NVCLBlobStoreAccessSvc nvclBlobStoreAccessSvc = null;
    NVCLDataSvc nvclDataSvc = null;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

    @Bean
    public NVCLDataSvcDao nvclDataSvcDao() {
		return new NVCLDataSvcDao();
    }

    @Bean
    public NVCLBlobStoreAccessSvc nvclBlobStoreAccessSvc() {
        if (this.nvclBlobStoreAccessSvc==null) {
            this.nvclBlobStoreAccessSvc = new NVCLBlobStoreAccessSvc(createConfig());
            return this.nvclBlobStoreAccessSvc;
        }
        else
            return this.nvclBlobStoreAccessSvc;
    }

    @Bean
    @ConfigurationProperties(prefix = "jdbc")
	public BasicDataSource dataSource() {
        if (this.ds==null) {
            this.ds = new BasicDataSource();
            this.ds.addConnectionProperty("defaultRowPrefetch","1000");
            return this.ds;
        }
        else return this.ds;
    }

	@Bean
	public DownSampledClassDataDao downSampledClassDataDao() throws SQLException {
		return new DownSampledClassDataDao(this.dataSource());
    }
    
	@Bean
	public DownSampledFloatDataDao downSampledFloatDataDao() {
		return new DownSampledFloatDataDao(this.dataSource());
    }

    @Bean
	public DomainDataDao domainDataDao() {
		return new DomainDataDao(this.dataSource());
    }

    @Bean
	public LogExtentsDao logExtentsDao() {
		return new LogExtentsDao(this.dataSource());
    }

    @Bean
	public NVCLDataSvc nvclDataSvc() {
        if (this.nvclDataSvc==null) {
            this.nvclDataSvc = new NVCLDataSvc();
            this.nvclDataSvc.setNVCLBlobStoreAccessSvc(nvclBlobStoreAccessSvc());
            return this.nvclDataSvc;
        }
        else {
            return this.nvclDataSvc;
        }
    }

    @Bean
    @ConfigurationProperties
	public ConfigVo createConfig() {
        if (this.config==null) {
            this.config = new ConfigVo();
            return this.config;
        }
        else return this.config;
    }

 /*   @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        if (this.connectionFactory==null) {
            this.connectionFactory = new ActiveMQConnectionFactory();
            //this.connectionFactory.setBrokerURL("vm://embedded");
            return this.connectionFactory;
        }
        else return this.connectionFactory;
    }*/

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
        if (this.jmsTemplate==null) {
            this.jmsTemplate = new JmsTemplate();
            this.jmsTemplate.setConnectionFactory(connectionFactory);
            this.jmsTemplate.setMessageConverter(nvclDownloadMessageConverter());
            return this.jmsTemplate;
        }
        else
            return this.jmsTemplate;
    }

    @Bean
    public MessageConverter nvclDownloadMessageConverter() {
        if (this.nvclDownloadMessageConverter==null) {
            this.nvclDownloadMessageConverter = new NVCLDownloadMessageConverter();
            return this.nvclDownloadMessageConverter;
        }
        else
            return this.nvclDownloadMessageConverter;

    }
    
    @Bean
    public ActiveMQQueue tsgRequestDestination(){
        if (this.tsgRequestDestination==null) {
            this.tsgRequestDestination = new ActiveMQQueue("tsgdownload.request.queue");
            return this.tsgRequestDestination;
        }
        else
            return this.tsgRequestDestination;
    }

    
    @Bean
    public ActiveMQQueue tsgReplyDestination(){
        if (this.tsgReplyDestination==null) {
            this.tsgReplyDestination = new ActiveMQQueue("tsgdownload.reply.queue");
            return this.tsgReplyDestination;
        }
        else
            return this.tsgReplyDestination;
    }

    @Bean
    public NVCLDownloadGateway nvclDownloadGateway(ConnectionFactory connectionFactory){
        if (this.nvclDownloadGateway==null) {
            this.nvclDownloadGateway = new NVCLDownloadGateway();
            this.nvclDownloadGateway.setJmsTemplate(jmsTemplate(connectionFactory));
            return this.nvclDownloadGateway;
        }
        else
            return this.nvclDownloadGateway;
    }

    @Bean
    public NVCLDownloadSvc nvclDownloadSvc(){
        if (this.nvclDownloadSvc==null) {
            this.nvclDownloadSvc = new NVCLDownloadSvc();
            this.nvclDownloadSvc.setConfig(createConfig());
            return this.nvclDownloadSvc;
        }
        else
            return this.nvclDownloadSvc;
    }


    @Bean
    public TSGDownloadRequestSvc tsgDownloadRequestSvc(ConnectionFactory connectionFactory){
        if (this.tsgDownloadRequestSvc==null) {
            this.tsgDownloadRequestSvc = new TSGDownloadRequestSvc();
            this.tsgDownloadRequestSvc.setJmsTemplate(jmsTemplate(connectionFactory));
            this.tsgDownloadRequestSvc.setDestination(tsgReplyDestination());
            this.tsgDownloadRequestSvc.setConfig(createConfig());
            this.tsgDownloadRequestSvc.setMailSender(mailSender());
            this.tsgDownloadRequestSvc.setNvclDownloadSvc(nvclDownloadSvc());
            return this.tsgDownloadRequestSvc;
        }
        else
            return this.tsgDownloadRequestSvc;
    }

    @Bean
    public NVCLDownloadQueueBrowser nvclDownloadQueueBrowser(ConnectionFactory connectionFactory){
        if (this.nvclDownloadQueueBrowser==null) {
            this.nvclDownloadQueueBrowser = new NVCLDownloadQueueBrowser();
            this.nvclDownloadQueueBrowser.setJmsTemplate(jmsTemplate(connectionFactory));
            return this.nvclDownloadQueueBrowser;
        }
        else
            return this.nvclDownloadQueueBrowser;
    }
    
    @Bean
    public MessageListenerAdapter tsgDownloadRequestListener(ConnectionFactory connectionFactory){
        if (this.tsgDownloadRequestListener==null) {
            this.tsgDownloadRequestListener = new MessageListenerAdapter();
            this.tsgDownloadRequestListener.setDelegate(tsgDownloadRequestSvc(connectionFactory));
            this.tsgDownloadRequestListener.setDefaultListenerMethod("processRequest");
            this.tsgDownloadRequestListener.setMessageConverter(nvclDownloadMessageConverter());
            this.tsgDownloadRequestListener.setDefaultResponseDestination(tsgReplyDestination());
            return this.tsgDownloadRequestListener;
        }
        else
            return this.tsgDownloadRequestListener;
    }
    
    @Bean
    public SimpleMessageListenerContainer tsgDownloadRequestContainer(ConnectionFactory connectionFactory){
        if (this.tsgDownloadRequestContainer==null) {
            this.tsgDownloadRequestContainer = new SimpleMessageListenerContainer();
            this.tsgDownloadRequestContainer.setConnectionFactory(connectionFactory);
            this.tsgDownloadRequestContainer.setDestination(tsgRequestDestination());
            this.tsgDownloadRequestContainer.setMessageListener(tsgDownloadRequestListener(connectionFactory));
            return this.tsgDownloadRequestContainer;
        }
        else
            return this.tsgDownloadRequestContainer;
    }

    @Bean
    @ConfigurationProperties(prefix = "smtp")
    public JavaMailSenderImpl mailSender(){
        if (this.mailSender==null) {
            this.mailSender = new JavaMailSenderImpl();
            return this.mailSender;
        }
        else
            return this.mailSender;
    } 

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[]{
            org.auscope.nvcl.server.vo.DatasetCollectionVo.class,
            org.auscope.nvcl.server.vo.ImageLogCollectionVo.class,
            org.auscope.nvcl.server.vo.LogCollectionVo.class,
            org.auscope.nvcl.server.vo.DomainDataCollectionVo.class,
            BoreholeViewVo.class,
            gmlPointVo.class,
            org.auscope.nvcl.server.vo.FeatureCollectionVo.class,
            org.auscope.nvcl.server.vo.BoreholeVo.class,
            org.auscope.nvcl.server.vo.BoreholeFeatureCollectionVo.class,
            org.auscope.nvcl.server.vo.SpectralLogCollectionVo.class,
            org.auscope.nvcl.server.vo.ProfLogCollectionVo.class,
            org.auscope.nvcl.server.vo.AlgorithmCollectionVo.class,
            org.auscope.nvcl.server.vo.ClassificationsCollectionVo.class,
            org.auscope.nvcl.server.vo.DepthRangeVo.class,
            org.auscope.nvcl.server.vo.downloadUrl.class
        });

        return marshaller;
    }

}