const AddAsset = () => {
    let states = [
        ["ASNEW", "As New"],
        ["FINE", "Fine"],
        ["VERYGOOD", "Very Good"],
        ["GOOD", "Good"],
        ["FAIR", "Fair"],
        ["POOR", "Poor"],
        ["EXLIBRARY", "Ex-Library"],
        ["BOOKCLUB", "Book Club"],
        ["BINDINGCOPY", "Binding Copy"],
    ]

    // TODO Check classes
    return (
        <div className="container">
            <h1 className="text-center mb-5">Do you want to borrow a book?</h1>
            <div className="p-4 container flex-container">
                <div className="image-wrapper">
                    <img src="/static/no_image_placeholder.jpg" alt="Book Cover" className="img-fluid object-fit-cover" /> 
                </div>
            </div>

            <div className="form-container">
                <div className='stepper'>
                </div>
                <fieldset>
                    <h2>ISBN</h2>
                    <text>We need the ISBN to get info about the book. If there's missing parts, you'll be asked to complete it.</text>
                    <input type="text" placeholder="ISBN" className='form-control'/>
                    <small className="text-danger small">Please input a valid ISBN</small>
                    <div className="button-container">
                        <input type='button' className='prev-button btn btn-outline-success mx-1' value='Previous' disabled/>
                        <input type='button' className='next-button btn btn-outline-success mx-1' value='Next'/>
                    </div>
                </fieldset>

                <fieldset>
                    <h2>Book Details</h2>
                    <div className='field-group'>
                        <div className='field'>
                            <label htmlFor='title' className='form-label'>Title</label>
                            <input type='text' className='form-control' id='title' placeholder='Title' readOnly />
                        </div>
                        <div className='field'>
                            <label htmlFor='physicalCondition' className='form-label'>Physical Condition</label>
                            <select id='physicalCondition' className='form-control'>
                                {states.map((state) => {
                                    return <option value={state[0]}>{state[1]}</option>
                                })}
                            </select>
                        </div>
                    </div>
                    <div className='field-group'>
                        <div className='field'>
                            <label htmlFor='author' className='form-label'>Author</label>
                            <input type='text' className='form-control' id='author' placeholder='Author' readOnly />
                        </div>
                        <div className='field'>
                            <label htmlFor='language' className='form-label'>Language</label>
                            <input type='text' className='form-control' id='language' placeholder='Language' readOnly />
                        </div>
                    </div>
                </fieldset>


            </div>
        </div>
    )
}

export default AddAsset;
