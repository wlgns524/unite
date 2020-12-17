package com.rightcode.unite.network.typeConverter;


import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.unite.Util.DataEnums;

public class ProfileImageConverter extends StringBasedTypeConverter<DataEnums.ProfileImageDiff> {

    @Override
    public DataEnums.ProfileImageDiff getFromString(String s) {
        return DataEnums.ProfileImageDiff.getEnum(s);
    }

    public String convertToString(DataEnums.ProfileImageDiff object) {
        return object == null ? "" : object.toString();
    }
}
