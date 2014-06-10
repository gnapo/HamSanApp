package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import view.LinePanel;

public class DeletedListener implements ActionListener {
	
	private JCheckBox myCheckbox;
	private LinePanel lp;

	public DeletedListener(JCheckBox deletedBox, LinePanel lp) {
		this.myCheckbox = deletedBox;
		this.lp = lp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lp.setDrawDeleted(myCheckbox.isSelected());
		lp.repaint();
	}

}
