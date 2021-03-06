package com.webank.weevent.broker.fabric.config;

import com.webank.weevent.broker.config.SmartLoadConfig;
import com.webank.weevent.broker.fisco.util.WeEventUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author websterchen
 * @version v1.1
 * @since 2019/8/9
 */
@Slf4j
@Data
@Component
@PropertySource(value = "classpath:fabric/fabric.properties", encoding = "UTF-8")
public class FabricConfig {
    @Value("${chain.channel.name:mychannel}")
    private String channelName;

    @Value("${chain.organizations.name:Org1}")
    private String orgName;

    @Value("${chain.organizations.mspid:Org1MSP}")
    private String mspId;

    @Value("${chain.organizations.username:Admin}")
    private String orgUserName;

    @Value("${chain.organizations.user.keyfile:}")
    private String orgUserKeyFile;

    @Value("${chain.organizations.user.certfile:}")
    private String orgUserCertFile;

    @Value("${chain.peer.address:}")
    private String peerAddress;

    @Value("${chain.peer.tls.cafile:}")
    private String peerTlsCaFile;

    @Value("${chain.orderer.address:}")
    private String ordererAddress;

    @Value("${chain.orderer.tls.cafile:}")
    private String ordererTlsCaFile;

    @Value("${chaincode.topic.version:v1.0}")
    private String topicVerison;

    @Value("${chaincode.topic.name:Topic}")
    private String topicName;

    private String topicSourceLoc;

    @Value("${chaincode.topic.path:contract/Topic}")
    private String topicPath;

    @Value("${chaincode.topic-controller.version:v1.0}")
    private String topicControllerVersion;

    @Value("${chaincode.topic-controller.name:TopicController}")
    private String topicControllerName;

    private String topicControllerSourceLoc;

    @Value("${chaincode.topic-controller.path:contract/TopicController}")
    private String topicControllerPath;

    @Value("${chaincode.proposal.timeout:12000}")
    private Long proposalTimeout;

    @Value("${chaincode.transaction.timeout:30000}")
    private Long transactionTimeout;

    @Value("${consumer.idle-time:1000}")
    private Integer consumerIdleTime;

    @Value("${consumer.history_merge_block:8}")
    private Integer consumerHistoryMergeBlock;

    /**
     * load configuration without spring
     *
     * @return true if success, else false
     */
    public boolean load() {
        boolean loadResult = new SmartLoadConfig().load(this, "");
        this.setOrgUserKeyFile(WeEventUtils.getClassPath() + this.getOrgUserKeyFile());
        this.setOrgUserCertFile(WeEventUtils.getClassPath() + this.getOrgUserCertFile());
        this.setOrdererTlsCaFile(WeEventUtils.getClassPath() + this.getOrdererTlsCaFile());
        this.setPeerTlsCaFile(WeEventUtils.getClassPath() + this.getPeerTlsCaFile());

        this.setTopicSourceLoc(WeEventUtils.getClassPath() + "fabric");
        this.setTopicControllerSourceLoc(WeEventUtils.getClassPath() + "fabric");

        return loadResult;
    }


}
