package info.russtyuf;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPrefDialog extends JFrame implements Runnable {

	private BindingGroup m_bindingGroup;
	private JPanel m_contentPane;
	private info.russtyuf.UserPref userPref = new info.russtyuf.UserPref();
	private JTextField emailJTextField;
	private JPasswordField passwordJPasswordField;
	private JButton btnOk;
	private Component verticalStrut;
	private Component horizontalStrut;
	private Component verticalStrut_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPrefDialog frame = new UserPrefDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserPrefDialog() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		m_contentPane = new JPanel();
		setContentPane(m_contentPane);
		//
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 1.0E-4 };
		m_contentPane.setLayout(gridBagLayout);

		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		m_contentPane.add(verticalStrut, gbc_verticalStrut);

		JLabel emailLabel = new JLabel("Email:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 1;
		m_contentPane.add(emailLabel, labelGbc_0);

		emailJTextField = new JTextField();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 5);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 1;
		m_contentPane.add(emailJTextField, componentGbc_0);

		JLabel passwordLabel = new JLabel("Password:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 2;
		m_contentPane.add(passwordLabel, labelGbc_1);

		passwordJPasswordField = new JPasswordField();
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 5);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 2;
		m_contentPane.add(passwordJPasswordField, componentGbc_1);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new BtnOkActionListener());
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 6;
		m_contentPane.add(btnOk, gbc_btnOk);

		verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 7;
		m_contentPane.add(verticalStrut_1, gbc_verticalStrut_1);

		horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridx = 2;
		gbc_horizontalStrut.gridy = 8;
		m_contentPane.add(horizontalStrut, gbc_horizontalStrut);

		if (userPref != null) {
			m_bindingGroup = initDataBindings();
		}
	}

	protected BindingGroup initDataBindings() {
		BeanProperty<info.russtyuf.UserPref, java.lang.String> emailProperty = BeanProperty
				.create("email");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty = BeanProperty
				.create("text");
		AutoBinding<info.russtyuf.UserPref, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, userPref,
						emailProperty, emailJTextField, textProperty);
		autoBinding.bind();
		//
		BeanProperty<info.russtyuf.UserPref, java.lang.String> passwordProperty = BeanProperty
				.create("password");
		BeanProperty<javax.swing.JPasswordField, java.lang.String> textProperty_1 = BeanProperty
				.create("text");
		AutoBinding<info.russtyuf.UserPref, java.lang.String, javax.swing.JPasswordField, java.lang.String> autoBinding_1 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, userPref,
						passwordProperty, passwordJPasswordField,
						textProperty_1);
		autoBinding_1.bind();
		//
		BindingGroup bindingGroup = new BindingGroup();
		bindingGroup.addBinding(autoBinding);
		bindingGroup.addBinding(autoBinding_1);
		//
		return bindingGroup;
	}

	public info.russtyuf.UserPref getUserPref() {
		return userPref;
	}

	public void setUserPref(info.russtyuf.UserPref newUserPref) {
		setUserPref(newUserPref, true);
	}

	public void setUserPref(info.russtyuf.UserPref newUserPref, boolean update) {
		userPref = newUserPref;
		if (update) {
			if (m_bindingGroup != null) {
				m_bindingGroup.unbind();
				m_bindingGroup = null;
			}
			if (userPref != null) {
				m_bindingGroup = initDataBindings();
			}
		}
	}

    @Override
    public void run() {
        setVisible(true);
    }

    private class BtnOkActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}
}
