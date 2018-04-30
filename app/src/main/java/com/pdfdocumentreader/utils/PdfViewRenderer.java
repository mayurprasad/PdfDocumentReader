package com.pdfdocumentreader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfViewRenderer {
    /**
     * File descriptor of the PDF.
     */
    private ParcelFileDescriptor mFileDescriptor;

    /**
     * {@link android.graphics.pdf.PdfRenderer} to render the PDF.
     */
    private android.graphics.pdf.PdfRenderer mPdfRenderer;

    /**
     * Page that is currently shown on the screen.
     */
    private android.graphics.pdf.PdfRenderer.Page mCurrentPage;

    FileModel fileModel;

    public PdfViewRenderer(Context context, FileModel fileModel) {
        this.fileModel = fileModel;
        try {
            openRenderer(context);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
     */
    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.
        File file = new File(fileModel.getPath());
        if (!file.exists()) {
            // Since PdfViewRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
            InputStream asset = context.getAssets().open(fileModel.getName());
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfViewRenderer we use to render the PDF.
        if (mFileDescriptor != null) {
            mPdfRenderer = new android.graphics.pdf.PdfRenderer(mFileDescriptor);
        }
    }

    /**
     * Closes the {@link android.graphics.pdf.PdfRenderer} and related resources.
     *
     * @throws java.io.IOException When the PDF file cannot be closed.
     */
    public void closeRenderer() throws IOException {
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        mPdfRenderer.close();
        mFileDescriptor.close();
    }

    /**
     * Shows the specified page of PDF to the screen.
     *
     * @param index The page index.
     */
    public Bitmap showPage(int index) {
        //pdfView.resetZoom();
        //float currentZoomLevel = pdfView.getCurrentZoom();
        if (mPdfRenderer.getPageCount() <= index) {
            return null;
        }
        // Make sure to close the current page before opening another one.
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        // Use `openPage` to open a specific page in PDF.
        mCurrentPage = mPdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
//        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
//                Bitmap.Config.ARGB_8888);
//        Bitmap bitmap = Bitmap.createBitmap(
//                getResources().getDisplayMetrics().densityDpi * mCurrentPage.getWidth() / 72,
//                getResources().getDisplayMetrics().densityDpi * mCurrentPage.getHeight() / 72,
//                Bitmap.Config.ARGB_8888 );
        Bitmap bitmap = Bitmap.createBitmap(
                2 * mCurrentPage.getWidth(),
                2 * mCurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);

//        Matrix matrix = new Matrix();
//        float dpiAdjustedZoomLevel = currentZoomLevel * DisplayMetrics.DENSITY_MEDIUM / getResources().getDisplayMetrics().densityDpi;
//        matrix.setScale(dpiAdjustedZoomLevel, dpiAdjustedZoomLevel);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        mCurrentPage.render(bitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        return bitmap;
    }

    /**
     * Gets the number of pages in the PDF. This method is marked as public for testing.
     *
     * @return The number of pages.
     */
    public int getPageCount() {
        return mPdfRenderer.getPageCount();
    }
}
