package com.framgia.fdms.screen.profile.export;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.utils.Utils;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.framgia.fdms.utils.Constant.FOLDER_NAME_FAMS;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.FontFamily.TIMES_ROMAN;
import static com.itextpdf.text.Font.ITALIC;

/**
 * Created by tuanbg on 6/14/17.
 */

public class ExportPresenter implements ExportContract.Presenter {
    private static final int NUMBER_COLUMN_TABLE = 4;
    private static final int FONT_SIZE = 5;
    private static final String FILE_NAME_SAVED_PDF = ".pdf";
    private static final String FILE_NAME_SAVED_DOCX = ".docx";
    private static final int VALUE_IMAGE = 100;
    private static final float sTextSize = 20.7f;
    private static final int WIDTH_PERCENTAGE = 100;
    private static final int INDENT_LEFT = 10;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int NORMAL_FONT_SIZE = 13;
    private static final float[] COLUMN_WIDTH_TABLE_HEADER = { 2, 3, 3 };
    private static final float[] COLUMN_WIDTH_TABLE_INFO = { 2, 1 };
    private static final float[] COLUMN_WIDTH_TABLE_DEVICE = { 1, 3, 3, 3 };
    private static final int COLUMN_TABLE_SIGNATURE = 2;
    private static final int COL_SPAN = 2;
    private static final float THINKNESS = 0.7f;
    private static final float Y_POSITION = -2f;
    private static final int TOTAL_LINE_SIGN = 6;
    private static final float PADDING_CELL = 5f;
    private static final int INDEX_REPORT = 0;
    private static final int INDEX_COMPANY = 2;
    private static final int INDEX_ISO = 3;
    private static final int INDEX_DELIVER = 0;
    private static final int INDEX_DATE = 5;
    private Image mImage;
    private Paragraph mParagraph;
    private User mUser;
    private CompositeSubscription mCompositeSubscription;
    private ExportContract.ViewModel mViewModel;
    private PdfPTable mTableHeader;
    private PdfPTable mTableInfo;
    private PdfPTable mTableDevices;
    private PdfPTable mTableSignature;
    private Paragraph mPledge;
    private Paragraph mHeader;

    public ExportPresenter(User user, ExportContract.ViewModel viewModel) {
        mUser = user;
        mCompositeSubscription = new CompositeSubscription();
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    private Object createPdfAssinment(List<Device> list) {
        if (mUser == null) {
            return new NullPointerException(mViewModel.getString(R.string.title_user_not_found));
        }
        String fullName = mUser.getId() + "_" + mUser.getFirstName() + mUser.getLastName();
        File exportDir = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME_FAMS);
        if (!exportDir.exists()) exportDir.mkdirs();
        File file = new File(exportDir, fullName + "_" + getCurentTime() + FILE_NAME_SAVED_PDF);
        OutputStream output;
        try {
            output = new FileOutputStream(file);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, output);
            document.open();
            getFileName();
            getTableHeader();
            getTableInfo();
            getHeader();
            getTableDevices(list);
            getPledge();
            getTableSignature();

            document.add(mTableHeader);
            document.add(new Paragraph("\n"));
            document.add(mTableInfo);
            document.add(new Paragraph("\n"));
            document.add(mHeader);
            document.add(new Paragraph("\n"));
            document.add(mTableDevices);
            document.add(new Paragraph("\n"));
            document.add(mPledge);
            document.add(new Paragraph("\n"));
            document.add(mTableSignature);
            document.close();
            return file.getPath();
        } catch (DocumentException | IOException e) {
            return new NullPointerException(e.getMessage());
        }
    }

    private void getTableHeader() {
        mTableHeader = new PdfPTable(COLUMN_WIDTH_TABLE_HEADER);
        mTableHeader.setWidthPercentage(WIDTH_PERCENTAGE);
        mTableHeader.addCell(mImage);
        String[] texts = mViewModel.getStringArray(R.array.text_table_header);
        for (int i = 0; i < texts.length; i++) {
            PdfPCell cell = new PdfPCell(new Paragraph(texts[i]));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(PADDING_CELL);
            switch (i) {
                case INDEX_REPORT:
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    break;
                case INDEX_COMPANY:
                    cell.setColspan(COL_SPAN);
                    break;
                case INDEX_ISO:
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    break;
            }
            mTableHeader.addCell(cell);
        }
    }

    private void getTableInfo() {
        mTableInfo = new PdfPTable(COLUMN_WIDTH_TABLE_INFO);
        mTableInfo.setWidthPercentage(WIDTH_PERCENTAGE);
        String[] texts = mViewModel.getStringArray(R.array.text_table_info);
        for (int i = 0; i < texts.length; i++) {
            Paragraph paragraph = new Paragraph(texts[i] + ": ");
            PdfPCell cell = new PdfPCell();
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPadding(PADDING_CELL);
            switch (i) {
                case INDEX_DELIVER:
                    paragraph.add(mUser.getFirstName() + " " + mUser.getLastName());
                    break;
                case INDEX_DATE:
                    paragraph.add(String.valueOf(getCurentTime()));
                    break;
            }
            cell.addElement(paragraph);
            mTableInfo.addCell(cell);
        }
    }

    private void getTableDevices(List<Device> devices) {
        if (devices.size() <= 0 || devices == null) return;
        mTableDevices = new PdfPTable(COLUMN_WIDTH_TABLE_DEVICE);
        mTableDevices.setWidthPercentage(WIDTH_PERCENTAGE);
        String[] texts = mViewModel.getStringArray(R.array.text_table_device);
        for (String text : texts) {
            PdfPCell cell = new PdfPCell(new Paragraph(text));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(PADDING_CELL);
            mTableDevices.addCell(cell);
        }
        for (int i = 0; i < devices.size(); i++) {
            int index = i + 1;
            PdfPCell cell = new PdfPCell(new Paragraph(index + ""));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(PADDING_CELL);
            mTableDevices.addCell(cell);
            cell.setPhrase(new Paragraph(devices.get(i).getProductionName()));
            mTableDevices.addCell(cell);
            cell.setPhrase(new Paragraph(new Paragraph(devices.get(i).getDeviceCode() + "")));
            mTableDevices.addCell(cell);
            mTableDevices.addCell("");
        }
    }

    private void getHeader() {
        mHeader = new Paragraph();
        mHeader.setIndentationLeft(INDENT_LEFT);
        mHeader.add(new Chunk(mViewModel.getString(R.string.title_delivery_device),
                new Font(TIMES_ROMAN, NORMAL_FONT_SIZE, BOLD)).setUnderline(THINKNESS, Y_POSITION));
    }

    private void getPledge() {
        mPledge = new Paragraph();
        mPledge.setIndentationLeft(INDENT_LEFT);
        mPledge.add(new Chunk(mViewModel.getString(R.string.title_report_pledge),
                new Font(TIMES_ROMAN, NORMAL_FONT_SIZE, ITALIC)));
    }

    private void getTableSignature() {
        mTableSignature = new PdfPTable(COLUMN_TABLE_SIGNATURE);
        mTableSignature.setWidthPercentage(WIDTH_PERCENTAGE);
        Paragraph paraReceiver = new Paragraph();
        paraReceiver.add(new Chunk(mViewModel.getString(R.string.title_receiver),
                new Font(TIMES_ROMAN, NORMAL_FONT_SIZE, BOLD)));
        paraReceiver.add(new Chunk(mViewModel.getString(R.string.title_sign_name)));
        for (int i = 0; i < TOTAL_LINE_SIGN; i++) {
            paraReceiver.add(new Chunk(Chunk.NEWLINE));
        }
        PdfPCell receiver = new PdfPCell(paraReceiver);
        receiver.setHorizontalAlignment(Element.ALIGN_CENTER);
        mTableSignature.addCell(receiver);

        Paragraph paraDeliver = new Paragraph();
        paraDeliver.add(new Chunk(mViewModel.getString(R.string.title_deliver),
                new Font(TIMES_ROMAN, NORMAL_FONT_SIZE, BOLD)));
        paraDeliver.add(new Chunk(mViewModel.getString(R.string.title_sign_name)));
        PdfPCell deliver = new PdfPCell(paraDeliver);
        deliver.setHorizontalAlignment(Element.ALIGN_CENTER);
        mTableSignature.addCell(deliver);
    }

    private Object createPdf(List<Device> list) {
        if (mUser != null) {
            String fullName = mUser.getId() + "_" + mUser.getFirstName() + mUser.getLastName();
            File exportDir = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME_FAMS);
            if (!exportDir.exists()) exportDir.mkdirs();
            File file = new File(exportDir, fullName + "_" + getCurentTime() + FILE_NAME_SAVED_PDF);
            OutputStream output;
            try {
                output = new FileOutputStream(file);
                Document document = new Document();
                PdfWriter.getInstance(document, output);
                document.open();
                getFileName();
                setHeaderReport();
                document.add(mImage);
                document.add(mParagraph);
                generatePDFTable(list).setPaddingTop(VALUE_IMAGE);
                document.add(generatePDFTable(list));
                document.close();
                return file.getPath();
            } catch (DocumentException | IOException e) {
                return new NullPointerException(e.getCause().getMessage());
            }
        }
        return new NullPointerException(mViewModel.getString(R.string.title_user_not_found));
    }

    private void setHeaderReport() {
        mParagraph = new Paragraph();
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(mViewModel.getString(R.string.title_devices_assignment_report));
        paragraph.setFont(FontFactory.getFont(FontFactory.COURIER, sTextSize));
        mParagraph.add(paragraph);
        mParagraph.add(new Paragraph(mViewModel.getString(R.string.title_full_name)
                + mUser.getFirstName()
                + " "
                + mUser.getLastName()));
        mParagraph.add(new Paragraph(mViewModel.getString(R.string.title_branch)));
        mParagraph.add(new Paragraph(mViewModel.getString(R.string.title_employcode))
                + mUser.getEmployeeCode());
    }

    private void getFileName() throws IOException, BadElementException {
        Drawable d = mViewModel.getDrawable(R.drawable.ic_logo_framgia);
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bmp = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, VALUE_IMAGE, stream);
        mImage = Image.getInstance(stream.toByteArray());
        mImage.scaleAbsolute(VALUE_IMAGE, VALUE_IMAGE);
    }

    private PdfPTable generatePDFTable(List<Device> devices) {
        PdfPTable table = new PdfPTable(NUMBER_COLUMN_TABLE);
        table.addCell(mViewModel.getString(R.string.title_device_name));
        table.addCell(mViewModel.getString(R.string.title_model_number));
        table.addCell(mViewModel.getString(R.string.title_serial_number));
        table.addCell(mViewModel.getString(R.string.title_assign_date));
        for (Device device : devices) {
            PdfPCell cellName = new PdfPCell(new Phrase(device.getProductionName()));
            table.addCell(cellName);

            PdfPCell cellModel = new PdfPCell(new Phrase(device.getModelNumber()));
            table.addCell(cellModel);

            PdfPCell cellSeri = new PdfPCell(new Phrase(device.getSerialNumber()));
            table.addCell(cellSeri);

            String boughtDate = Utils.getStringDate(device.getBoughtDate());
            PdfPCell cellAssign = new PdfPCell(new Phrase(boughtDate));
            table.addCell(cellAssign);
        }
        return table;
    }

    public String getCurentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return dateFormat.format(cal.getTime());
    }

    private Object createDoc(List<Device> list) {
        // TODO: 28/06/2017
        return new NullPointerException(mViewModel.getString(R.string.title_user_not_found));
    }

    @Override
    public void exportDeviceByPdf(List<Device> list) {
        Observable.just(createPdfAssinment(list))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof String) {
                            String filePath = (String) o;
                            mViewModel.onExportPdfSuccess(filePath);
                        } else {
                            if (o instanceof NullPointerException) {
                                mViewModel.showMessage(R.string.message_export_error);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.showMessage(R.string.message_export_error);
                    }
                });
    }

    @Override
    public void exportDeviceByDoc(List<Device> list) {
        Observable.just(createDoc(list))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof String) {
                            String filePath = (String) o;
                            mViewModel.onExportDocSuccess(filePath);
                        } else {
                            if (o instanceof NullPointerException) {
                                mViewModel.showMessage(R.string.message_export_error);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mViewModel.showMessage(R.string.message_export_error);
                    }
                });
    }

    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
    }
}
