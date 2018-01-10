package org.espixxl.controller.rest;

import org.espixxl.controller.producer.Sender;
import org.espixxl.kafka.avro.Contribution;
import org.espixxl.kafka.avro.PiiField;
import org.espixxl.kafka.avro.comment_type_values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David Espinosa.
 */
@RestController("contribution")
public class ContributionController {

    @Autowired
    private Sender sender;

    @RequestMapping()
    public String send() {

        PiiField commentPiiField = PiiField.newBuilder()
                .setEncrypted(true)
                .setIdentityId(2)
                .setKeyManagementUrl("http://someurl.haufe/id/" + 2)
                .setType("string")
                .build();

        Contribution contribution = Contribution.newBuilder()
                .setComment("My comment")
                .setContributorId(2)
                .setContributorName(commentPiiField)
                .setCommentType(comment_type_values.SIMPLE)
                .build();
        sender.send(contribution);

        return "contribution sent";
    }

    @PostMapping()
    public ContributionDto postContribution(@RequestBody ContributionDto contributionDto) {

        PiiField contributorNamePiiField = getPiiField(contributionDto.getContributor_id(), "string", contributionDto.getContributor_name());
        PiiField contributorEmailPiiField = getPiiField(contributionDto.getContributor_id(), "string", contributionDto.getContributor_email());

        Contribution contribution = Contribution.newBuilder()
                .setComment(contributionDto.getComment())
                .setContributorId(contributionDto.getContributor_id())
                .setContributorName(contributorNamePiiField)
                .setContributorEmail(contributorEmailPiiField)
                .setCommentType(comment_type_values.SIMPLE)
                .build();

        sender.send(contribution);

        return contributionDto;
    }

    private PiiField getPiiField(int identityId, String baseType, Object value) {
        PiiField commentPiiField = PiiField.newBuilder()
                .setEncrypted(true)
                .setEncryptedValue(randomEncryption())
                .setIdentityId(identityId)
                .setKeyManagementUrl("http://someurl.haufe/id/" + identityId)
                .setType(baseType)
                .build();

        return commentPiiField;
    }

    public static String randomEncryption() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
