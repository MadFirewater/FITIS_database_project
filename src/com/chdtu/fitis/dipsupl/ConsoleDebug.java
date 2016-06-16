package com.chdtu.fitis.dipsupl;

import com.chdtu.fitis.dipsupl.entity.Department;
import com.chdtu.fitis.dipsupl.entity.Group;

import java.util.List;

import static com.chdtu.fitis.dipsupl.Query.getSelectedGroups;
import static com.chdtu.fitis.dipsupl.docmanager.DocumentBuilder.createDocumentForGroupById;
import static com.chdtu.fitis.dipsupl.docmanager.DocumentBuilder.createDocumentForStudentById;

public class ConsoleDebug {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        createDocumentForStudentById(4117);
          createDocumentForGroupById(368,"");//Overloaded! THe second parameter is a path.
//        List<Group> groups = getSelectedGroups(Department.DEPARTMENT_ID_FITIS);
//        for (Group group : groups)
//            createDocumentForGroupById(group.getId(),"");
        System.out.println("Execution time:" + (System.currentTimeMillis() - startTime) / 1000. + " seconds");
        System.exit(0);
    }
}
