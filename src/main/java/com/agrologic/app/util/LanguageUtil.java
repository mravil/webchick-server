
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.util;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.Language;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {Insert class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} (MM YYYY)
 * @author Valery Manakhimov
 * @author $Author: nbweb $, (this version)
 */
public class LanguageUtil {
    public static Language makeLang(ResultSet rs) throws SQLException {
        Language lang = new Language();

        lang.setId(rs.getLong("ID"));
        lang.setLanguage(rs.getString("Lang"));
        lang.setShortLang(rs.getString("Short"));

        return lang;
    }

    public static Collection<Language> makeLangList(ResultSet rs) throws SQLException {
        List<Language> langList = new ArrayList<Language>();

        while (rs.next()) {
            langList.add(makeLang(rs));
        }

        return langList;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
