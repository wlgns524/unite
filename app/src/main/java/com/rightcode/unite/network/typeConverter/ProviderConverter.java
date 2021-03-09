package com.rightcode.unite.network.typeConverter;


import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.unite.Util.DataEnums;

public class ProviderConverter extends StringBasedTypeConverter<DataEnums.Provider> {

    @Override
    public DataEnums.Provider getFromString(String s) {
        return DataEnums.Provider.getEnum(s);
    }

    public String convertToString(DataEnums.Provider object) {
        return object == null ? "" : object.toString();
    }
}
