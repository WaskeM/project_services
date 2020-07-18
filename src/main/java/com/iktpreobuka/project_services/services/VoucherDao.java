package com.iktpreobuka.project_services.services;

import com.iktpreobuka.project_services.entities.BillEntity;
import com.iktpreobuka.project_services.entities.VoucherEntity;

public interface VoucherDao {
	public VoucherEntity createVoucher(BillEntity bill);
	
}
