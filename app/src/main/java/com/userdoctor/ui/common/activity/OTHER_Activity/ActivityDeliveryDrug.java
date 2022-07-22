package com.userdoctor.ui.common.activity.OTHER_Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.userdoctor.R;
import com.userdoctor.ui.common.activity.Home_Activity.ActivityHome;
import com.userdoctor.ui.common.utils.NetworkUtil;
import com.userdoctor.ui.common.utils.PathUtils;
import com.userdoctor.ui.common.utils.ToastClass;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileOutputStream;

public class ActivityDeliveryDrug extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_capture, iv_doc,back;
    private File imgFile;
    private LinearLayout ll_gallary, ll_doc, ll_pdf_confirm;
    private TextView tv_document, tv_upload;
    private String serverDoc;
    private Dialog dialog;
Intent intent;
    Uri selectedPdfUri;
    private String serverResume;
    File pdfFile;
    private File file;
    private String uploadedFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_drug);
        NetworkUtil.isNetworkConnected(ActivityDeliveryDrug.this);

        initView();
        clickListner();
    }


    private void initView() {

        iv_capture = findViewById(R.id.iv_capture);
        tv_document = findViewById(R.id.tv_document);
        tv_upload = findViewById(R.id.tv_upload);
        ll_pdf_confirm = findViewById(R.id.ll_pdf_confirm);
        // iv_doc = findViewById(R.id.iv_doc);
        back= findViewById(R.id.iv_back);

    }

    private void clickListner() {
        iv_capture.setOnClickListener(this);
        tv_upload.setOnClickListener(this);
        back.setOnClickListener(this);
        //iv_doc.setOnClickListener(this);
    }

    private void openFile(File url) {

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (url.toString().contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_capture:
                //ImagePicker();
                openMediaDialog();
                break;

            case R.id.ll_gallary:
                dialog.dismiss();
                ImagePicker();
                break;

            case R.id.ll_doc:
                dialog.dismiss();
                selectDoc();
                break;

            case R.id.iv_back:
                intent = new Intent(ActivityDeliveryDrug.this, ActivityHome.class);
                startActivity(intent);

                break;


            case R.id.tv_upload:
                if (imgFile == null) {
                    ToastClass.showToast(this, "Please select Image/doc");
                } else {
                    finish();
                }

                break;
        }
    }

    private void selectDoc() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        //startActivityForResult(intent, 7);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), 7);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            if (requestCode == 7) {
//                Uri selectedImageUri = data.getData();
//                serverDoc = selectedImageUri.getPath();
//
//                //String serverUrlAid = PathUtils.getFilePathFromContentUri(context, selectedImageUri);
//
//                Log.e("serverDoc = ", serverDoc);
//                File file = new File(serverDoc);
//                String uploadedFileName = file.getName();
//                Log.e("File name=", "File : " + file.getName());
//                if (uploadedFileName != null && !uploadedFileName.equals("")) {
//                    tv_document.setText(uploadedFileName);
//                    tv_document.setVisibility(View.VISIBLE);
//                } else {
//                    tv_document.setVisibility(View.GONE);
//                    Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
//                }
//            }

            //******************pdf path show***
            if (requestCode == 7) {
                selectedPdfUri = data.getData();
                serverResume = selectedPdfUri.getPath();
                pdfFile = new File(selectedPdfUri.getPath());
                //String serverUrlAid = PathUtils.getFilePathFromContentUri(context, selectedImageUri);
                Log.e("pdfFile = ", pdfFile.toString());
                file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        serverResume);
//                file = new File(serverResume);
                uploadedFileName = file.getName();
                Log.e("File_name=", "File : " + file.getName());
                if (uploadedFileName != null && !uploadedFileName.equals("")) {
                    tv_document.setText(file.getPath());
                    tv_document.setVisibility(View.VISIBLE);
                    ll_pdf_confirm.setVisibility(View.VISIBLE);

                    setPdfThumbnails(selectedPdfUri);
                } else {
                    Toast.makeText(ActivityDeliveryDrug.this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }

            }


        }
    }

    private void setPdfThumbnails(Uri pdfUri) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(this);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
        }
    }

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";
    private void saveImage(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "PDF.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }

        try {
            //if (bmp!=null)
            iv_capture.setImageBitmap(bmp);
        }catch (Exception e){

        }



    }

    private void ImagePicker() {
        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
        dialog.setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                if (r.getError() == null) {

                    //getImageView().setImageURI(r.getUri());
                    //If you want the Bitmap.
                    tv_document.setVisibility(View.GONE);
                    ll_pdf_confirm.setVisibility(View.VISIBLE);
                    iv_capture.setImageBitmap(r.getBitmap());

                    Log.e("Imagepath", r.getPath());

                    imgFile = PathUtils.bitmapToFile(ActivityDeliveryDrug.this, r.getBitmap());
                    Log.e("imgFile", "" + imgFile);

                    //r.getPath();
                } else {
                    //Handle possible errors
                    //TODO: do what you have to do with r.getError();
                    ToastClass.showToast(ActivityDeliveryDrug.this, r.getError().getMessage());
                }
            }

            // }).show((FragmentActivity) context);
        }).show(ActivityDeliveryDrug.this);

    }

    @SuppressLint("WrongConstant")
    private void openMediaDialog() {
        dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_media);

        ll_gallary = dialog.findViewById(R.id.ll_gallary);
        ll_doc = dialog.findViewById(R.id.ll_doc);

        ll_gallary.setOnClickListener(this);
        ll_doc.setOnClickListener(this);

        dialog.show();

    }
}
