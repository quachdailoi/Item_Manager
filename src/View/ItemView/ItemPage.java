/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ItemView;

import javax.swing.JButton;

/**
 *
 * @author DELL
 */
public class ItemPage {
    JButton btnPage;

    public ItemPage(JButton btnPage) {
        this.btnPage = btnPage;
    }

    public JButton getBtnPage() {
        return btnPage;
    }

    public void setBtnPage(JButton btnPage) {
        this.btnPage = btnPage;
    }


    public int getIndexOfPage() {
        return Integer.parseInt(btnPage.getText());
    }

    public void setIndexOfPage(int indexOfPage) {
        btnPage.setText(indexOfPage+"");
    }
}
