package drools;



import java.io.UnsupportedEncodingException;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;

//@SpringBootApplication()
public class MainTest {
    public static void main(String[] args) {
        //rule,rule2可以放在数据库中，有个唯一code和他们对于，代码要执行规则的时候，根据code从数据库获取出来就OK了，这样自己开发的规则管理系统那边对数据库里的规则进行维护就行了
        String rule = "package com.fei.drools\r\n";
        rule += "import drools.Message;\r\n";
        rule += "rule \"rule1\"\r\n";
        rule += "\twhen\r\n";
        rule += "Message( status == 1, myMessage : msg )";
        rule += "\tthen\r\n";
        rule += "\t\tSystem.out.println( 1+\":\"+myMessage );\r\n";
        rule += "end\r\n";


        String rule2 = "package com.fei.drools\r\n";
        rule += "import drools.Message;\r\n";

        rule += "rule \"rule2\"\r\n";
        rule += "\twhen\r\n";
        rule += "Message( status == 2, myMessage : msg )";
        rule += "\tthen\r\n";
        rule += "\t\tSystem.out.println( 2+\":\"+myMessage );\r\n";
        rule += "end\r\n";


        StatefulKnowledgeSession kSession = null;
        try {


            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
            //装入规则，可以装入多个
            kb.add(ResourceFactory.newByteArrayResource(rule.getBytes(StandardCharsets.UTF_8)), ResourceType.DRL);
            kb.add(ResourceFactory.newByteArrayResource(rule2.getBytes(StandardCharsets.UTF_8)), ResourceType.DRL);

            KnowledgeBuilderErrors errors = kb.getErrors();
            for (KnowledgeBuilderError error : errors) {
                System.out.println(error);
            }
            KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
            kBase.addKnowledgePackages(kb.getKnowledgePackages());

            kSession = kBase.newStatefulKnowledgeSession();


            Message message1 = new Message();
            message1.setStatus(1);
            message1.setMsg("hello world!");

            Message message2 = new Message();
            message2.setStatus(2);
            message2.setMsg("hi world!");

            kSession.insert(message1);
            kSession.insert(message2);
            kSession.fireAllRules();

        } finally {
            if (kSession != null) {
                kSession.dispose();
            }
        }

    }

}
