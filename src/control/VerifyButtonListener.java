package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifyButtonListener implements ActionListener {

	private HamSanAlg hsa;
	
	public VerifyButtonListener(HamSanAlg h) {
		this.hsa = h;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (hsa.validSol(true)) {
			System.out.println("solution is valid! hooray");
		}
		else {
			System.out.println("solution not valid. boo.");
		}

	}

}
