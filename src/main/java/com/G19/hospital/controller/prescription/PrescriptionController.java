import React from 'react';

const PrescriptionDetails = ({ prescription }) => {
  if (!prescription) {
    return (
      <div className="flex items-center justify-center h-screen text-lg font-semibold text-gray-600">
        No prescription data available
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-4xl font-bold text-center mb-10 text-gray-800">
        Prescription Details
      </h1>

      {/* Prescription Info Card */}
      <div className="bg-white rounded-xl shadow-md p-6 mb-10">
        <h2 className="text-3xl font-semibold mb-4 text-gray-800 border-b pb-2">
          Prescription Info
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div>
            <p className="text-sm text-gray-500">ID</p>
            <p className="text-lg text-gray-800 font-medium">{prescription.id}</p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Prescription Number</p>
            <p className="text-lg text-gray-800 font-medium">{prescription.prescriptionNumber}</p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Date Issued</p>
            <p className="text-lg text-gray-800 font-medium">
              {new Date(prescription.dateIssued).toLocaleString()}
            </p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Doctor ID</p>
            <p className="text-lg text-gray-800 font-medium">{prescription.doctorId}</p>
          </div>
          <div>
            <p className="text-sm text-gray-500">Patient ID</p>
            <p className="text-lg text-gray-800 font-medium">{prescription.patientId}</p>
          </div>
          {prescription.bookingAppointmentId && (
            <div>
              <p className="text-sm text-gray-500">Booking Appointment ID</p>
              <p className="text-lg text-gray-800 font-medium">{prescription.bookingAppointmentId}</p>
            </div>
          )}
          <div className="md:col-span-3">
            <p className="text-sm text-gray-500">General Instructions</p>
            <p className="text-lg text-gray-800 font-medium">{prescription.generalInstructions}</p>
          </div>
        </div>
      </div>

      {/* Prescription Items Table */}
      <div className="bg-white rounded-xl shadow-md overflow-hidden">
        <div className="bg-gray-200 px-6 py-3">
          <h2 className="text-2xl font-semibold text-gray-800">Prescription Items</h2>
        </div>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-300">
            <thead className="bg-gray-100">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  ID
                </th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Dosage
                </th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Frequency
                </th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Duration
                </th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                  Additional Instructions
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200 bg-white">
              {prescription.prescriptionItems && prescription.prescriptionItems.length > 0 ? (
                prescription.prescriptionItems.map((item) => (
                  <tr key={item.id} className="hover:bg-gray-50 transition-colors duration-200">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{item.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{item.dosage}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{item.frequency}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{item.duration}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-700">{item.additionalInstructions}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" className="px-6 py-4 text-center text-sm text-gray-500">
                    No prescription items found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default PrescriptionDetails;

