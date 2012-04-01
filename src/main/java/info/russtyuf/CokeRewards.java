package info.russtyuf;

//~--- non-JDK imports --------------------------------------------------------

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

//~--- JDK imports ------------------------------------------------------------

/**
 * Class description
 * 
 * 
 * @version Enter version here..., 12/03/16
 * @author Rusty Stiles
 */
public class CokeRewards {
	private McrClient client;
	private Codes codes;
	private JComboBox<Integer> comboBox;
	private JFrame frmMyRewards;
	private JSpinner jSpinnerSubmit;
	private UserPref pref;
	private UserPrefDialog prefDialog;
	private ArrayList<Integer> reverseValues;
	private JTextArea textAreaStatus;
	private JTextField textFieldCodes;


	/**
	 * Create the application.
	 */
	public CokeRewards() {
		initialize();
		client = new McrClient();
    pref = new UserPref();
    prefDialog = new UserPrefDialog();

    codes = getCodes();
    reverseValues = new ArrayList<Integer>();
    Collections.addAll(reverseValues, Codes.VALUES);
    Collections.sort(reverseValues, Collections.reverseOrder());
	}

  private Codes getCodes() {
    Codes cds = new Codes();
    if (pref.getCodeFile().exists()) {
      cds.openMerge(pref.getCodeFile());
    }
    return cds;
  }

  /**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CokeRewards window = new CokeRewards();

					window.frmMyRewards.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMyRewards = new JFrame();
		frmMyRewards.setTitle("My Rewards");
		frmMyRewards.setBounds(100, 100, 450, 300);
		frmMyRewards.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		frmMyRewards.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("",
				"[1px][81px,grow][180px,grow][5px][89px]",
				"[1px][14px][20px][23px][20px][23px][][grow]"));

		JLabel lblPointValue = new JLabel("Point Value:");

		panel.add(lblPointValue, "cell 1 1,growx,aligny top");

		JLabel lblCodesToBe = new JLabel("Codes to be saved:");

		panel.add(lblCodesToBe, "cell 2 1,alignx right,aligny top");
		comboBox = new JComboBox<Integer>();
		comboBox.setToolTipText("Pick the value of the code(s) being added");
		comboBox.setModel(new DefaultComboBoxModel<Integer>(Codes.VALUES));
		panel.add(comboBox, "cell 1 2,growx,aligny top");
		textFieldCodes = new JTextField();
		textFieldCodes
				.setToolTipText("Codes to be saved, Separated by a comma");
		textFieldCodes.setColumns(10);
		panel.add(textFieldCodes, "cell 2 2 3 1,growx,aligny top");

		JButton btnSaveCodes = new JButton("Save Codes");

		btnSaveCodes.addActionListener(new BtnSaveCodesActionListener());
		panel.add(btnSaveCodes, "cell 4 3,alignx left,aligny top");

		JLabel lblPointsTo = new JLabel("Points to \r\nSubmit:");

		panel.add(lblPointsTo, "cell 1 4,alignx left,aligny center");
		jSpinnerSubmit = new JSpinner();
		jSpinnerSubmit.setModel(new SpinnerNumberModel(120, 3, 120, 1));
		panel.add(jSpinnerSubmit, "cell 2 4 3 1,growx,aligny top");

		JButton btnSubmit = new JButton("    Submit    ");

		btnSubmit.addActionListener(new BtnSubmitActionListener());
		panel.add(btnSubmit, "cell 4 5,alignx left,aligny top");

		JScrollPane scrollPane = new JScrollPane();

		panel.add(scrollPane, "cell 1 7,grow,span");
		textAreaStatus = new JTextArea();
		scrollPane.setViewportView(textAreaStatus);

		JMenuBar menuBar = new JMenuBar();

		frmMyRewards.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");

		menuBar.add(mnFile);

		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.setToolTipText("Export codes to a plain text file");

		mntmExport.addActionListener(new MntmExportActionListener());

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new MntmSaveActionListener());
		mntmSave.setToolTipText("Force a save of the codes to default file");
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new MntmSaveAsActionListener());
		mntmSaveAs.setToolTipText("Save a code file to chosen file");
		mnFile.add(mntmSaveAs);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new MntmOpenActionListener());
		mntmOpen.setToolTipText("Open a code file");
		mnFile.add(mntmOpen);
		mnFile.add(mntmExport);

		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.setToolTipText("Import file created by Export");

		mntmImport.addActionListener(new MntmImportActionListener());
		mnFile.add(mntmImport);

    JMenuItem mntmReport = new JMenuItem("Report");
    mntmReport.setToolTipText("Get a report on the codes database");
    mntmReport.addActionListener(new MntmReportActionListener());
    mnFile.add(mntmReport);

		JMenu mnEdit = new JMenu("Edit");

		menuBar.add(mnEdit);

		JMenuItem mntmPreferences = new JMenuItem("Preferences");

		mntmPreferences.addActionListener(new MntmPreferencesActionListener());
		mnEdit.add(mntmPreferences);
	}

	/**
	 * Method description
	 * 
	 * 
	 * @param total
	 * @param value
	 * 
	 * @return
	 */
	private NextCode getNextCode(int total, int value) {
		for (int val : reverseValues) {
			if ((total + val) <= value && codes.isCodeOfValue(val)) {
				return new NextCode(codes.getCode(val), val);
			}
		}

		return null;
	}

	private class BtnSaveCodesActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
      Thread t = new Thread(new Saver());
      t.start();
		}
	}

	private class BtnSubmitActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Thread t = new Thread(new Submitter());
      t.start();
		}
	}

  private class Submitter implements Runnable {

    @Override
    public void run() {
      int total = 0;

      try {
        jSpinnerSubmit.commitEdit();
      } catch (ParseException e1) {
        e1.printStackTrace();
      }

      int value = (Integer) jSpinnerSubmit.getValue();
      int numOfCodes = 0;
      String email = pref.getEmail();
      String password = pref.getPassword();
      String version = pref.getVersion();

      if ((email == null) || (password == null)) {
        prefDialog.run();
        textAreaStatus
            .append("Incorrect User Credentials, Enter correct "
                + "Credentials and try again.\n ");

        return;
      }

      LoginResult login = client.login(client.createLoginParams(
          email, password, version));

      textAreaStatus.append(login.toString() + "\n");

      if (!login.loginResult) {
        return;
      }

      while (total <= value) {
        NextCode codeValue = getNextCode(total, value);

        if (codeValue == null) {
          textAreaStatus.append("Finished: " + "Submitted "
              + numOfCodes + " for " + total
              + " points.\n");

          break;
        }

        Object[] pParams = client.createSubmitCodeParams(email,
            password, version, codeValue.code);
        SubmitResult submit = client.submitCode(pParams);

        if (submit.messages.contains("Weekly Point limit met.")) {
          break;
        } else if (submit.messages
            .contains("Code already redeemed.")
            || submit.messages
            .contains("Sorry, invalid code.")) {
          codes.removeCode(codeValue.value, codeValue.code);
        }

        if (submit.enterCodeResult) {
          total += submit.pointsEarned;
          codes.removeCode(codeValue.value, codeValue.code);
          numOfCodes++;
          textAreaStatus.append(submit.resultMsg(codeValue.code));
        }
      }

    }
  }

	private class MntmExportActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JFileChooser exportFile = new JFileChooser();

			exportFile.showSaveDialog(frmMyRewards);
			codes.export(exportFile.getSelectedFile());
		}
	}

	private class MntmImportActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JFileChooser importDialog = new JFileChooser();

			importDialog.showOpenDialog(frmMyRewards);
			codes.openImport(importDialog.getSelectedFile());
		}
	}


	private class MntmPreferencesActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			prefDialog.run();
		}
	}

	private class MntmOpenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
      JFileChooser openFileChooser = new JFileChooser();
      openFileChooser.showOpenDialog(frmMyRewards);
      codes.openMerge(openFileChooser.getSelectedFile());
		}
	}

	private class MntmSaveAsActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
      JFileChooser saveAsChooser = new JFileChooser();
      saveAsChooser.showSaveDialog(frmMyRewards);
      codes.save(saveAsChooser.getSelectedFile());
		}
	}

	private class MntmSaveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
      codes.save(pref.getCodeFile());
		}
	}

	private static class NextCode {
		public final String code;
		public final int value;

		private NextCode(String code, int value) {
			this.code = code;
			this.value = value;
		}
	}

  private class Saver implements Runnable {
    @Override
    public void run() {
      int value = comboBox.getItemAt(comboBox.getSelectedIndex());
      Splitter mySplitter = Splitter.on(",").trimResults()
          .omitEmptyStrings();
      ArrayList<String> codeList = Lists.newArrayList(mySplitter
          .split(textFieldCodes.getText()));

      for (String c : codeList) {
        if (codes.checkCode(c)) {
          codes.addCode(value, c);
          textAreaStatus.append(c + ": saved for " + value
              + " points.\n");
        } else {
          textAreaStatus.append(c + ": not added - "
              + codes.whyNotValidCode(c) + "\n");
        }
      }

      textFieldCodes.setText("");
    }
  }

  private class MntmReportActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
      textAreaStatus.append("\n" + codes.makeReport());
    }
  }
}
