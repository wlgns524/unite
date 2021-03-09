package com.rightcode.unite.Activity.Partner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Activity.MapActivity;
import com.rightcode.unite.Activity.Product.ProductSearchActivity;
import com.rightcode.unite.Adapter.SpinnerAdapter.CommonSpinnerAdapter;
import com.rightcode.unite.Fragment.TopFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.DateUtil;
import com.rightcode.unite.Util.FragmentUtil;
import com.rightcode.unite.Util.ToastUtil;
import com.rightcode.unite.network.model.response.product.Product;
import com.rightcode.unite.network.requester.partner.PartnerRegisterRequester;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.unite.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.unite.Util.ExtraData.EXTRA_PRODUCT;

public class PartnerRegisterActivity extends BaseActivity implements Spinner.OnItemSelectedListener {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.tv_partner)
    TextView tv_partner;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.ll_product)
    LinearLayout ll_product;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.ll_area)
    LinearLayout ll_area;
    @BindView(R.id.tv_start_date)
    TextView tv_start_date;
    @BindView(R.id.tv_end_date)
    TextView tv_end_date;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.sn_age)
    Spinner sn_age;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_gender_man)
    TextView tv_gender_man;
    @BindView(R.id.tv_gender_woman)
    TextView tv_gender_woman;
    @BindView(R.id.et_people)
    EditText et_people;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_content)
    EditText et_content;


    private TopFragment mTopFragment;
    private Product product;
    private String diff = "상품";
    private String gender;
    private String preText;
    private int number;
    private CommonSpinnerAdapter spinnerAdapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_register);

        ButterKnife.bind(this);
        initialize();
    }


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    product = (Product) data.getSerializableExtra(EXTRA_PRODUCT);
                    tv_product_name.setText(product.getName());
                    tv_product_name.setVisibility(View.VISIBLE);
                    break;
                }
                case EXTRA_ACTIVITY_COMPLETE: {
                    setResult(RESULT_OK);
                    finishWithAnim();
                }
                default:
                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        tv_age.setText(spinnerAdapter.getItem(position).toString());
        tv_age.setVisibility(View.VISIBLE);
        sn_age.setVisibility(View.GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_partner, R.id.tv_area, R.id.ll_area, R.id.tv_product_search, R.id.tv_start_date, R.id.tv_end_date,
            R.id.tv_gender, R.id.tv_age, R.id.tv_gender_man, R.id.tv_gender_woman, R.id.tv_register})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_partner: {
                diff = "상품";
                tv_partner.setSelected(true);
                tv_area.setSelected(false);
                tv_product_name.setVisibility(product != null ? View.VISIBLE : View.GONE);
                ll_product.setVisibility(View.VISIBLE);
                ll_area.setVisibility(View.GONE);
                break;
            }
            case R.id.tv_area: {
                diff = "지역";
                tv_partner.setSelected(false);
                tv_area.setSelected(true);
                ll_product.setVisibility(View.GONE);
                ll_area.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.ll_area: {
                Intent intent = new Intent(PartnerRegisterActivity.this, MapActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_product_search: {
                Intent intent = new Intent(PartnerRegisterActivity.this, ProductSearchActivity.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.tv_start_date: {
                DatePickerDialog dialog = new DatePickerDialog(PartnerRegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (view1, year, monthOfYear, dayOfMonth) ->
                                tv_start_date.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth))
                        , DateUtil.getYearToInt(), DateUtil.getMonthToInt() - 1, DateUtil.getDayToInt());
//                dialog.getDatePicker().setCalendarViewShown(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                break;
            }
            case R.id.tv_end_date: {
                DatePickerDialog dialog = new DatePickerDialog(PartnerRegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (view1, year, monthOfYear, dayOfMonth) ->
                                tv_end_date.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth))
                        , DateUtil.getYearToInt(), DateUtil.getMonthToInt() - 1, DateUtil.getDayToInt());
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                break;
            }
            case R.id.tv_gender: {
                gender = "무";
                tv_gender.setSelected(true);
                tv_gender_man.setSelected(false);
                tv_gender_woman.setSelected(false);
                break;
            }
            case R.id.tv_gender_man: {
                gender = "남";
                tv_gender.setSelected(false);
                tv_gender_man.setSelected(true);
                tv_gender_woman.setSelected(false);
                break;
            }
            case R.id.tv_gender_woman: {
                gender = "여";
                tv_gender.setSelected(false);
                tv_gender_man.setSelected(false);
                tv_gender_woman.setSelected(true);
                break;
            }
            case R.id.tv_register: {
                if (isValid()) {
                    partnerRegister();
                }
                break;
            }
            case R.id.tv_age: {
                tv_age.setVisibility(View.GONE);
                sn_age.setVisibility(View.VISIBLE);
                sn_age.performClick();
            }
        }
    }

    private void initialize() {
        if (getIntent() != null) {
            product = (Product) getIntent().getSerializableExtra(EXTRA_PRODUCT);
            if (product != null) {
                tv_product_name.setText(product.getName());
                tv_product_name.setVisibility(View.VISIBLE);
            }
        }
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "동행 구하기");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.ic_back);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, v -> finishWithAnim());
        tv_partner.setSelected(true);

//        et_age.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                preText = charSequence.toString();
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().equals(preText))
//                    return;
//                //editText에 포커스가 되어있고 텍스트가 하나라도 입력되어 있을때 동작하기 위해서 추가.
//                if (et_age.isFocusable() && !charSequence.toString().equals("")) {
//                    try {
//                        number = Integer.parseInt(et_age.getText().toString());
//                        //100이 넘을 경우 100으로 변경
//                        if (number > 100) {
//                            number = 100;
//                        }
//                        if (number % 10 > 5) {
//                            number = number / 10;
//                            number = number * 10;
//                            number = number + 10;
//                        } else {
//                            number = number / 10;
//                            number = number * 10;
//                        }
//                        et_age.setText(String.format("%d", number));
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                        return;
//                    }
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        //Spinner Adapter
        spinnerAdapter = new CommonSpinnerAdapter(PartnerRegisterActivity.this);
        //Adapter 적용
        sn_age.setAdapter(spinnerAdapter);
        sn_age.setOnItemSelectedListener(this);
    }

    private Boolean isValid() {
        if (tv_partner.isSelected() && product == null) {
            ToastUtil.show(PartnerRegisterActivity.this, "동행상품을 선택해주세요");
            return false;
        }

        if (TextUtils.isEmpty(tv_start_date.getText())) {
            ToastUtil.show(PartnerRegisterActivity.this, "동행시작날짜를 선택해주세요");
            return false;
        }

        if (TextUtils.isEmpty(tv_end_date.getText())) {
            ToastUtil.show(PartnerRegisterActivity.this, "동행종료날짜를 선택해주세요");
            return false;
        }

        if (gender == null) {
            ToastUtil.show(PartnerRegisterActivity.this, "원하는 동행 성별을 선택해주세요");
            return false;
        }

        if (TextUtils.isEmpty(tv_age.getText())) {
            ToastUtil.show(PartnerRegisterActivity.this, "원하는 동행 연령대를 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_people.getText())) {
            ToastUtil.show(PartnerRegisterActivity.this, "구하는 인원을 입력해주세요");
            return false;
        }

        if (Integer.parseInt(et_people.getText().toString()) <= 0) {
            ToastUtil.show(PartnerRegisterActivity.this, "1명이상을 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_title.getText())) {
            ToastUtil.show(PartnerRegisterActivity.this, "제목을 입력해주세요");
            return false;
        }

        if (TextUtils.isEmpty(et_content.getText())) {
            ToastUtil.show(PartnerRegisterActivity.this, "내용을 입력해주세요");
            return false;
        }

        return true;
    }

    private void partnerRegister() {
        PartnerRegisterRequester requester = new PartnerRegisterRequester(PartnerRegisterActivity.this);
        requester.setDiff(diff);
        if (diff.equals("상품")) {
            requester.setProductId(product.getId());
        } else {

        }
        requester.setStartDate(tv_start_date.getText().toString());
        requester.setEndDate(tv_end_date.getText().toString());
        requester.setGender(gender);
        requester.setAge(tv_age.getText().toString());
        requester.setPeople(et_people.getText().toString());
        requester.setTitle(et_title.getText().toString());
        requester.setContent(et_content.getText().toString());
        showLoading();
        request(requester,
                success -> {
                    hideLoading();
                    if (success.getCode() == 200) {
                        ToastUtil.show(PartnerRegisterActivity.this, "등록되었습니다");
                        setResult(RESULT_OK);
                        finishWithAnim();
                    } else {
                        showServerErrorDialog(success.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });

    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
