package com.omneagate.dpc.Model;


import com.omneagate.dpc.Model.EnumModel.TableNames;

import lombok.Data;

/**
 * Created by user1 on 29/5/15.
 */
@Data
public class FistSyncInputDto {
    String tableName;
    int count;
    String textToDisplay;
    String endTextToDisplay;
    boolean dynamic;
    TableNames tableNames;
    String LocalTableName;
}
