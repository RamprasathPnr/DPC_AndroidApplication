package com.omneagate.dpc.Model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by root on 4/8/16.
 */
@Data
public class VersionUpgradeDto extends BaseResponseDto implements Serializable{


    private static final long serialVersionUID = 1L;

    long id;

    /**
     * upgrade version
     */

    int version;

    /**
     * released date
     */

    long releaseDate;

    /**
     * server location
     */

    String location;


}
