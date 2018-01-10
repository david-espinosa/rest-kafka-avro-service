package org.espixxl.controller.rest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author David Espinosa.
 */
@Getter
@Setter
public class ContributionDto {

    private int contributor_id;
    private String contributor_name;
    private String contributor_email;
    private String comment;
}
