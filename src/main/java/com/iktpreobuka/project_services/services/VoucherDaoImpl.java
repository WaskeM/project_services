package com.iktpreobuka.project_services.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project_services.entities.BillEntity;
import com.iktpreobuka.project_services.entities.VoucherEntity;
import com.iktpreobuka.project_services.repositories.VoucherRepository;

@Service
public class VoucherDaoImpl implements VoucherDao{

	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public VoucherEntity createVoucher(BillEntity bill) {
		VoucherEntity voucher = new VoucherEntity();
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 7);
        
        voucher.setBuyer(bill.getBuyer());
        voucher.setOffer(bill.getOffer());
        voucher.setExpirationDate(cal.getTime());
		voucher.setUsed(false);
		
		try {
			emailService.sendTemplateMessage(voucher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return voucherRepository.save(voucher);
	}

}
