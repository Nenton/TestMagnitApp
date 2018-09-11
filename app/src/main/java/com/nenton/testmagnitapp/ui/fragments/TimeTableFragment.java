package com.nenton.testmagnitapp.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.testmagnitapp.R;
import com.nenton.testmagnitapp.data.json.ResultParse;
import com.nenton.testmagnitapp.data.json.Station;
import com.nenton.testmagnitapp.data.managers.DataManager;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.Calendar;
import java.util.Locale;

public class TimeTableFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String SEND_STATION_KEY = "SEND_STATION_KEY";
    private static final String ARRIVE_STATION_KEY = "ARRIVE_STATION_KEY";
    private static final String SEND_DATE_KEY = "SEND_DATE_KEY";
    private static final String SEND_SHOW_KEY = "SEND_SHOW_KEY";
    private static final String ARRIVE_SHOW_KEY = "ARRIVE_SHOW_KEY";
    private boolean isEditSend;
    private boolean isEditArrive;
    private boolean isShowInfoSend;
    private boolean isShowInfoArrive;
    private Station stationSend;
    private Station stationArrive;
    private ConstraintLayout layoutSend;
    private ConstraintLayout layoutArrive;
    private ImageView dropImgSend;
    private ImageView dropImgArrive;
    private static ResultParse resultParse;
    private TextView textDateView;
    private ConstraintLayout layout;
    private AutoCompleteTextView sendStationACTV;
    private AutoCompleteTextView arriveStationACTV;
    private View view;
    private TextWatcher sendWatcher;
    private TextWatcher arriveWatcher;

    public TimeTableFragment() {
        setRetainInstance(true);
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (resultParse == null) {
            resultParse = DataManager.getInstance().readFile(container.getContext());
        }
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_time_table, container, false);
            layoutSend = view.findViewById(R.id.wrapInfoSend);
            layoutArrive = view.findViewById(R.id.wrapInfoArrive);
            dropImgSend = view.findViewById(R.id.dropImgSend);
            dropImgArrive = view.findViewById(R.id.dropImgArrive);
            textDateView = view.findViewById(R.id.textDateView);
            layout = view.findViewById(R.id.fragment_container);
            initWatchers(getContext());
            initEditTexts(view, container);
            initDropImages(container);
            initDatePicker(view, container);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() != null){
            sendStationACTV.setTextColor(getContext().getResources().getColor(R.color.greyBlack));
            arriveStationACTV.setTextColor(getContext().getResources().getColor(R.color.greyBlack));
        }
        updateWrapInfoSendStation(true);
        updateWrapInfoArriveStation(true);
    }

    /**
     * Инициализация блока с выбором даты отправления. В блоке текстопое поле и кнопка
     *
     * @param view      Представление, в котором находятся элементы (текстовое поле и кнопка
     * @param container Родительский контейнер для фрагмента
     */
    private void initDatePicker(View view, final ViewGroup container) {
        Button button = view.findViewById(R.id.editDateBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(container.getContext(),
                        TimeTableFragment.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }

    /**
     * Слушатель изменений в диалоге выбора даты
     *
     * @param datePicker Элемент представления
     * @param i          Год
     * @param i1         Месяц
     * @param i2         День
     */
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = String.format(Locale.ENGLISH, "%02d/%02d/%4d", i2, i1 + 1, i);
        textDateView.setText(date);
    }

    /**
     * Инициализация выпадающего списка с информацией по станции
     *
     * @param container Родительский контейнер для фрагмента
     */
    private void initDropImages(final ViewGroup container) {
        if (dropImgSend != null) {
            dropImgSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStateSendInfo(container.getContext());
                }
            });
        }
        if (dropImgArrive != null) {
            dropImgArrive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStateArriveInfo(container.getContext());
                }
            });
        }
    }

    /**
     * Изменение состояния отображения блока с информацией о станции прибытия
     *
     * @param context Родительский контекст для фрагмента
     */
    private void changeStateArriveInfo(Context context) {
        isShowInfoArrive = !isShowInfoArrive;
        if (isShowInfoArrive) {
            dropImgArrive.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_less_24dp));
            TransitionManager.beginDelayedTransition(layout, new TransitionSet()
                    .addTransition(new ChangeBounds().setDuration(300))
                    .addTransition(new Fade().setDuration(300)).setOrdering(TransitionSet.ORDERING_SEQUENTIAL));
            layoutArrive.setVisibility(View.VISIBLE);
        } else {
            dropImgArrive.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_more_24dp));
            TransitionManager.beginDelayedTransition(layout, new TransitionSet()
                    .addTransition(new Fade().setDuration(300))
                    .addTransition(new ChangeBounds().setDuration(300)).setOrdering(TransitionSet.ORDERING_SEQUENTIAL));
            layoutArrive.setVisibility(View.GONE);
        }
    }

    /**
     * Изменение состояния отображения блока с информацией о станции отправления
     *
     * @param context Родительский контекст для фрагмента
     */
    private void changeStateSendInfo(Context context) {
        isShowInfoSend = !isShowInfoSend;
        if (isShowInfoSend) {
            dropImgSend.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_less_24dp));
            TransitionManager.beginDelayedTransition(layout, new TransitionSet()
                    .addTransition(new ChangeBounds().setDuration(300))
                    .addTransition(new Fade().setDuration(300)).setOrdering(TransitionSet.ORDERING_SEQUENTIAL));
            layoutSend.setVisibility(View.VISIBLE);
        } else {
            dropImgSend.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_more_24dp));
            TransitionManager.beginDelayedTransition(layout, new TransitionSet()
                    .addTransition(new Fade().setDuration(300))
                    .addTransition(new ChangeBounds().setDuration(300)).setOrdering(TransitionSet.ORDERING_SEQUENTIAL));
            layoutSend.setVisibility(View.GONE);
        }
    }

    /**
     * Инициализация AutoCompleteTextView слушателем изменения текста и выбора конкретного элемента
     *
     * @param view      Представление, на котором располагаются элементы
     * @param container Родительский контейнер для фрагмента
     */
    private void initEditTexts(View view, final ViewGroup container) {
        sendStationACTV = view.findViewById(R.id.sendStationACTV);
        arriveStationACTV = view.findViewById(R.id.arriveStationACTV);

        sendStationACTV.setAdapter(DropDownAdapter.initAdapter(container.getContext(), android.R.layout.simple_dropdown_item_1line, resultParse.getAllStationsFrom()));
        arriveStationACTV.setAdapter(DropDownAdapter.initAdapter(container.getContext(), android.R.layout.simple_dropdown_item_1line, resultParse.getAllStationsTo()));
        sendStationACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isEditSend = false;
                sendStationACTV.setTextColor(container.getContext().getResources().getColor(R.color.greyBlack));
                sendStationACTV.clearFocus();
                hideKeyboardFrom(getContext(), getView());
                stationSend = ((Station) adapterView.getItemAtPosition(i));
                updateWrapInfoSendStation(true);
            }
        });
        arriveStationACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isEditArrive = false;
                arriveStationACTV.setTextColor(container.getContext().getResources().getColor(R.color.greyBlack));
                arriveStationACTV.clearFocus();
                hideKeyboardFrom(getContext(), getView());
                stationArrive = ((Station) adapterView.getItemAtPosition(i));
                updateWrapInfoArriveStation(true);
            }
        });
        sendStationACTV.addTextChangedListener(sendWatcher);
        arriveStationACTV.addTextChangedListener(arriveWatcher);
    }


    private void initWatchers(final Context context) {
        if (sendWatcher == null){
            sendWatcher = new CustomTextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    sendStationACTV.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    isEditSend = true;
                    updateWrapInfoSendStation(false);
                }
            };
        }
        if (arriveWatcher == null){
            arriveWatcher = new CustomTextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    arriveStationACTV.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    isEditArrive = true;
                    updateWrapInfoArriveStation(false);
                }
            };
        }
    }

    /**
     * Метод скрывает клавиатуру
     *
     * @param context Контекст сущности
     * @param view    Элемент в фокусе
     */
    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Обновление информации о станции отправления
     *
     * @param show указывает отображать информацию или нет
     */
    private void updateWrapInfoSendStation(boolean show) {
        TransitionManager.beginDelayedTransition(layout);
        if (show && stationSend != null){
            ((TextView) layoutSend.findViewById(R.id.countryTitle)).setText(stationSend.getCountryTitle());
            ((TextView) layoutSend.findViewById(R.id.district)).setText(stationSend.getDistrictTitle());
            ((TextView) layoutSend.findViewById(R.id.city)).setText(stationSend.getCityTitle());
            ((TextView) layoutSend.findViewById(R.id.region)).setText(stationSend.getRegionTitle());
            dropImgSend.setVisibility(View.VISIBLE);
        } else {
            dropImgSend.setVisibility(View.GONE);
        }
    }

    /**
     * Обновление информации о станции прибытия
     *
     * @param show указывает отображать информацию или нет
     */
    private void updateWrapInfoArriveStation(boolean show) {
        TransitionManager.beginDelayedTransition(layout);
        if (show && stationArrive != null){
            ((TextView) layoutArrive.findViewById(R.id.countryTitle)).setText(stationArrive.getCountryTitle());
            ((TextView) layoutArrive.findViewById(R.id.district)).setText(stationArrive.getDistrictTitle());
            ((TextView) layoutArrive.findViewById(R.id.city)).setText(stationArrive.getCityTitle());
            ((TextView) layoutArrive.findViewById(R.id.region)).setText(stationArrive.getRegionTitle());
            dropImgArrive.setVisibility(View.VISIBLE);
        } else {
            dropImgArrive.setVisibility(View.GONE);
        }
    }

    /**
     * Кастомный TextWatcher.
     * Сделан для удобства переопределения лишь 1 метода onTextChanged
     */
    private abstract class CustomTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
