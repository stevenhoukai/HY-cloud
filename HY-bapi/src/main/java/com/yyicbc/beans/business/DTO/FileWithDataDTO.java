package com.yyicbc.beans.business.DTO;

import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.imports.PO.FormatFieldPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.imports.PO.TxtColumnFieldPO;
import com.yyicbc.beans.imports.PO.TxtTitleFieldPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileWithDataDTO implements Serializable {
    List<FovaUpdateDataPO> detailData = new ArrayList<FovaUpdateDataPO>();
    UserImportPO sumData ;
}