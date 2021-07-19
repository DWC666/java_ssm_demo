package junit.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ActivitiTest {
    private ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    private ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

    /**
     * 流程引擎
     */
    @Test
    public void test1(){
        System.out.println("ProcessEngine: -------> " + processEngine);
    }

    /**
     * 流程定义部署
     */
    @Test
    public void test2(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess1.bpmn").deploy();
        System.out.println("deploy: -------> " + deploy);
    }

    /**
     * 查询流程定义
     */
    @Test
    public void test3(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for(ProcessDefinition p : list){
            System.out.println(p.getId());
            System.out.println(p.getKey());
            System.out.println(p.getName());
            System.out.println("----------------");
        }

    }

}
