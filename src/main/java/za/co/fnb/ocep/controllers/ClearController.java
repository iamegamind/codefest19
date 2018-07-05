package za.co.fnb.ocep.controllers;

import org.apache.log4j.Logger;

import za.co.fnb.pe.framework.api.UIClearController;
import za.co.fnb.pe.framework.api.UIControllerContext;

public class ClearController implements UIClearController {
	private static final Logger LOG = Logger.getLogger( ClearController.class );

	@Override
	public void processMessage(UIControllerContext context) {
		LOG.debug("Clearing all data!");
	}
}
