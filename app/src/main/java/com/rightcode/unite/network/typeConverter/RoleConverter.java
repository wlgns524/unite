package com.rightcode.unite.network.typeConverter;


import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.unite.Util.DataEnums;

public class RoleConverter extends StringBasedTypeConverter<DataEnums.RoleDiff> {

    @Override
    public DataEnums.RoleDiff getFromString(String s) {
        return DataEnums.RoleDiff.getEnum(s);
    }

    public String convertToString(DataEnums.RoleDiff object) {
        return object == null ? "" : object.toString();
    }
}
