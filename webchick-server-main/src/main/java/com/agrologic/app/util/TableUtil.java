
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.util;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.model.Table;

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
public class TableUtil {
    public static Table makeTable(ResultSet rs) throws SQLException {
        Table table = new Table();
        table.setId(rs.getLong("TableID"));
        table.setTitle(rs.getString("Title"));
        table.setScreenId(rs.getLong("ScreenID"));
        table.setProgramId(rs.getLong("ProgramID"));
        table.setDisplay(rs.getString("DisplayOnScreen"));
        table.setPosition(rs.getInt("Position"));

        try {
            table.setLangId(rs.getLong("LangId"));
        } catch (SQLException e) {
            table.setLangId((long)1);
        }
        // if unicode doesn't occur in result set ignore
        try {
            if ((rs.getString("UnicodeTitle") != null) && (rs.getString("UnicodeTitle").length() > 0)) {
                table.setUnicodeTitle(rs.getString("UnicodeTitle"));
            } else {
                table.setUnicodeTitle(rs.getString("Title"));
            }
        } catch (SQLException e) {
            /* ignore */
            table.setUnicodeTitle(rs.getString("Title"));
        }
        return table;
    }

    public static Collection<Table> makeTableList(ResultSet rs) throws SQLException {
        List<Table> tables = new ArrayList<Table>();

        while (rs.next()) {
            tables.add(makeTable(rs));
        }

        return tables;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
