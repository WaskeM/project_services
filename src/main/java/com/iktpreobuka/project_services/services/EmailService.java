package com.iktpreobuka.project_services.services;

import com.iktpreobuka.project_services.entities.VoucherEntity;

public interface EmailService {
	
	void sendTemplateMessage (VoucherEntity voucher) throws Exception;
	
}
