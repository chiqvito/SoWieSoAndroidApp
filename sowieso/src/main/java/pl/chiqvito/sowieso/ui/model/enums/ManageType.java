package pl.chiqvito.sowieso.ui.model.enums;

import pl.chiqvito.sowieso.R;

public enum ManageType implements IEnumResorces {

    EDIT, REMOVE;

    @Override
    public int resIdString() {
        switch (this) {
            case EDIT:
                return R.string.manage_type_edit;
            case REMOVE:
                return R.string.manage_type_remove;
        }
        throw new IllegalArgumentException("Unknown string res id for " + this);
    }

}
