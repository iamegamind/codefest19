package za.co.fnb.ocep.controllers;

import org.apache.log4j.Logger;
import za.co.fnb.pe.framework.api.UIController;
import za.co.fnb.pe.framework.api.UIControllerContext;

public class DetermineEReceiptsEnabilityController implements UIController {
    private static final Logger LOG = Logger.getLogger(ClearController.class);

    @Override
    public String processMessage(UIControllerContext context) {

        boolean registered = false;

        if (registered) {
            LOG.debug("User is enabled for eReceipts");
            return "eReceiptsEnabled";
        } else {
            LOG.debug("User is NOT enabled for eReceipts");
            return "eReceiptsDisabled";
        }
    }
}
