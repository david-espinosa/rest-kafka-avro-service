package org.espixxl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David Espinosa.
 */
@RestController
public class ContributionController {

    @RequestMapping("contribution")
    public String send() {
        return "hi";
    }
}
